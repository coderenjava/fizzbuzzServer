package com.leboncoin.backoffice.controller;

import com.leboncoin.backoffice.common.FizzBuzzConstants;
import com.leboncoin.backoffice.common.FizzBuzzUtils;
import com.leboncoin.backoffice.exception.InvalidFizzBuzzParamsException;
import com.leboncoin.backoffice.service.FizzBuzzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@WebFilter("/*")
public class FizzBuzzController implements Filter {

	private static final Logger logger = LoggerFactory.getLogger( FizzBuzzController.class);
	private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
			Arrays.asList("", "/stats")));

	@Autowired
	private FizzBuzzService fizzBuzzService;

	@GetMapping(value = "/fizzbuzz", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String buildFizzBuzzText(@RequestParam final String int1, @RequestParam final String int2, @RequestParam final String limit, @RequestParam final String str1, @RequestParam final String str2) {
		logger.info("Starting FizzBuzz API with parameters: int1 {}, int2 {}, limmit {}, str1 {}, str2 {} ", int1, int2, limit, str1, str2);
		String response;
		String messageError = FizzBuzzUtils.valdiateRequest(int1, int2, limit, str1, str2);
		if(!messageError.isEmpty()) {
			throw new InvalidFizzBuzzParamsException(new StringBuilder(FizzBuzzConstants.TYPE_ERROR_INVALID_PARAMETERS).append(messageError).toString());
		}
		try {
			response = fizzBuzzService.createFizzBuzz(Integer.valueOf(int1), Integer.valueOf(int2), Integer.valueOf(limit), str1, str2);
		} catch (Exception e){
				throw new ServerErrorException("Other reason", e.getCause());
		}
		return response;
	}

	@GetMapping("/stats")
	@ResponseBody
	public Map calculateMostUsedRequest() {
		logger.info("Building statistics...");
		return fizzBuzzService.returnStatistics();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		chain.doFilter(request, response);
		if(!ALLOWED_PATHS.contains(httpRequest.getRequestURI())) {
			String requestWithParams = httpRequest.getMethod() + " " + httpRequest.getRequestURI() + "?" + httpRequest.getQueryString();
			fizzBuzzService.buildStatistics(requestWithParams);
		}
	}
}
