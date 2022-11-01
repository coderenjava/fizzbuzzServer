package com.leboncoin.backoffice.controller;

import com.leboncoin.backoffice.Application;
import com.leboncoin.backoffice.service.FizzBuzzService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ServerErrorException;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class FizzBuzzControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private FizzBuzzService fizzBuzzService;
    private String expected = "1,2,fizz,4,buzz,";

    @Test
    void shouldBuildFizzBuzzText() throws Exception {
        Mockito.when(fizzBuzzService.createFizzBuzz(anyInt(), anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(expected);
        mvc.perform(get("/fizzbuzz")
                .param("int1", "3")
                .param("int2", "5")
                .param("limit", "5")
                .param("str1", "fizz")
                .param("str2", "buzz"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnInvalidFizzBuzzParamsException() throws Exception {
        mvc.perform(get("/fizzbuzz")
                .param("int1", "3,3")
                .param("int2", "cinq")
                .param("limit", "5")
                .param("str1", "fizz")
                .param("str2", "buzz"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnServerErrorException() throws Exception {
        Mockito.when(fizzBuzzService.createFizzBuzz(anyInt(), anyInt(), anyInt(), anyString(), anyString()))
                .thenThrow(ServerErrorException.class);
        mvc.perform(get("/fizzbuzz")
                .param("int1", "3")
                .param("int2", "5")
                .param("limit", "5")
                .param("str1", "fizz")
                .param("str2", "buzz"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldCalculateStatistics() throws Exception {
        Mockito.when(fizzBuzzService.returnStatistics())
                .thenReturn(new HashMap<>());
        mvc.perform(get("/stats"))
                .andExpect(status().isOk());
    }
}
