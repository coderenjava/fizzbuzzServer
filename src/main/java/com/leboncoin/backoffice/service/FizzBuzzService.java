package com.leboncoin.backoffice.service;

import com.leboncoin.backoffice.common.FizzBuzzUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Service
public class FizzBuzzService  {

    private Map<String, Integer> metricMap;
    public FizzBuzzService() {
        metricMap = new ConcurrentHashMap<>();
    }

    public String createFizzBuzz(final Integer number1, final Integer number2, final Integer limit, final String fizzText, final String buzzText) {
        StringBuilder response = new StringBuilder();
        IntStream.rangeClosed(1, limit)
                .mapToObj(i -> i % number2 == 0 ? (i % number1 == 0 ? new StringBuilder(fizzText).append(buzzText).toString() : buzzText) : (i % number1 == 0 ? fizzText : i))
                .forEach(o -> response.append(o).append(','));
        return response.substring(0, response.length() - 1);
    }

    public Map<String, Integer> buildStatistics(String requestWithParams) {
        Integer count = metricMap.get(requestWithParams);
        if (count == null) {
            count = 1;
        } else {
            count++;
        }
        metricMap.put(requestWithParams, count);
        return metricMap;
    }

    public Map<String, Integer> returnStatistics() {
        return FizzBuzzUtils.elementWithMaxValue(metricMap);
    }

}
