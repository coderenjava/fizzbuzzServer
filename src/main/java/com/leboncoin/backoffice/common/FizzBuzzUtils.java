package com.leboncoin.backoffice.common;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class FizzBuzzUtils {

    public static String valdiateRequest(final String number1, final String number2, final String limit, final String fizzText, final String buzzText) {

        StringBuilder messageError = new StringBuilder("");

        if (!number1.matches(FizzBuzzConstants.INTEGER_REGEX) || !number2.matches(FizzBuzzConstants.INTEGER_REGEX) || !limit.matches(FizzBuzzConstants.INTEGER_REGEX))
        {
            messageError.append(FizzBuzzConstants.INT1_INT2_MUST_BE_INTEGERS);
        } else {
            if (Integer.valueOf(number2) == 0 || Integer.valueOf(number1) == Integer.valueOf(number2)) messageError.append(FizzBuzzConstants.INT1_INT2_MUST_BE_DIFFERENT_AND_INT2_NOT_EQUAL_TO_0);
            if (Integer.valueOf(number1) >= Integer.valueOf(limit) || Integer.valueOf(number2) > Integer.valueOf(limit)) messageError.append(FizzBuzzConstants.INT1_INT2_MUST_BE_LESS_THAN_LIMIT);
            if (fizzText.isEmpty() || buzzText.isEmpty()) messageError.append(FizzBuzzConstants.STR1_STR2_LENGHT);
        }

        return messageError.toString();
    }

    public static Map<String, Integer> elementWithMaxValue(Map<String, Integer> map) {
        Integer maxValueInMap = Collections.max(map.values());
        return map.entrySet()
                .stream()
                .filter(m -> maxValueInMap == m.getValue())
                .collect(Collectors.toMap(mm -> mm.getKey(), vv -> vv.getValue()));
    }
}
