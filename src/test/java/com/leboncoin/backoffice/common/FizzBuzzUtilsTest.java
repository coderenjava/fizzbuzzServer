package com.leboncoin.backoffice.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class FizzBuzzUtilsTest {

    @Test
    public void shouldGetErrorWhenInt1OrInt2OrLimitAreNotIntegers() {
        Assertions.assertNotEquals(FizzBuzzUtils.valdiateRequest("trois", "5,2", "100", "fizz", "buzz").length(), 0);
    }

    @Test
    public void shouldGetErrorWhenInt1OrInt2AreEqualOrInt2IsEqualToZero() {
        Assertions.assertNotEquals(FizzBuzzUtils.valdiateRequest("3", "0", "100", "fizz", "buzz").length(), 0);
    }

    @Test
    public void shouldGetErrorWhenInt1OrInt2AreGreaterThanLmit() {
        Assertions.assertNotEquals(FizzBuzzUtils.valdiateRequest("3", "200", "100", "fizz", "buzz").length(), 0);
    }

    @Test
    public void shouldGetErrorWhenStr1OrStr2AreEmpty() {
        Assertions.assertNotEquals(FizzBuzzUtils.valdiateRequest("3", "5", "100", "", "buzz").length(), 0);
    }

    @Test
    public void shouldValidateRequest() {
        Assertions.assertEquals(FizzBuzzUtils.valdiateRequest("3", "5", "100", "fizz", "buzz").length(), 0);
    }

    @Test
    public void shouldgetElementWithMaxValueInMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("GET /fizzbuzz?int1=3&int2=5&limit=100&str1=fizz&str2=buzz", 4);
        map.put("GET /fizzbuzz?int1=3&int2=5&limit=200&str1=fizz&str2=buzz", 2);

        Assertions.assertEquals(FizzBuzzUtils.elementWithMaxValue(map).size(), 1);
        Assertions.assertEquals(FizzBuzzUtils.elementWithMaxValue(map).get("GET /fizzbuzz?int1=3&int2=5&limit=100&str1=fizz&str2=buzz"), 4);
    }
}
