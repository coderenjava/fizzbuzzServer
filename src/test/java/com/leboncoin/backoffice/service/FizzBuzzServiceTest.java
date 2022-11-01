package com.leboncoin.backoffice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class FizzBuzzServiceTest {

    @InjectMocks
    private FizzBuzzService fizzBuzzService;

    @Test
    public void createFizzBuzz() {
        Assertions.assertNotEquals(fizzBuzzService.createFizzBuzz(3, 5, 100, "fizz", "buzz").length(), 0);
    }

    @Test
    public void shouldBuildStatistics_Case_New_Request_So_Count_Is_Equal_To_1() {
        String requestAndParams = "GET /fizzbuzz?int1=3&int2=50&limit=5&str1=fizz&str2=buzz";
        Assertions.assertEquals(fizzBuzzService.buildStatistics(requestAndParams).get(requestAndParams), 1);
    }

    @Test
    public void shouldBuildStatistics_Case_Existing_Request_So_Count_Is_To_Increment() {
        String requestAndParams = "GET /fizzbuzz?int1=3&int2=50&limit=5&str1=fizz&str2=buzz";
        fizzBuzzService.buildStatistics(requestAndParams);
        Assertions.assertEquals(fizzBuzzService.buildStatistics(requestAndParams).get(requestAndParams), 2);
    }

    @Test
    public void shouldReturnMostUsedRequest() {
        fizzBuzzService.buildStatistics("GET /fizzbuzz?int1=3&int2=5&limit=100&str1=fizz&str2=buzz");
        fizzBuzzService.buildStatistics("GET /fizzbuzz?int1=3&int2=5&limit=100&str1=fizz&str2=buzz");
        fizzBuzzService.buildStatistics("GET /fizzbuzz?int1=3&int2=5&limit=100&str1=fizz&str2=buzz");
        Map<String, Integer> mostUsedRequest = fizzBuzzService.buildStatistics("GET /fizzbuzz?int1=3&int2=5&limit=100&str1=fizz&str2=buzz");

        fizzBuzzService.buildStatistics("GET /fizzbuzz?int1=3&int2=100&limit=50&str1=fizz&str2=buzz");

        fizzBuzzService.buildStatistics("GET /fizzbuzz?int1=3&int2=50&limit=200&str1=fizz&str2=buzz");
        fizzBuzzService.buildStatistics("GET /fizzbuzz?int1=3&int2=50&limit=200&str1=fizz&str2=buzz");

        Assertions.assertEquals(mostUsedRequest.get("GET /fizzbuzz?int1=3&int2=5&limit=100&str1=fizz&str2=buzz"), 4);
    }
}
