package com.dailycodingproblem.problems;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

public class Problem1 {

    //Given a list of numbers and a number k, return whether any two numbers from the list add up to k.
    //
    //For example, given [10, 15, 3, 7] and k of 17, return true since 10 + 7 is 17.
    //
    //Bonus: Can you do this in one pass?

    public static void main(String[] args) {
        Assertions.assertTrue(hasGivenSum(new int[]{10, 15, 3, 7}, 17));
        Assertions.assertFalse(hasGivenSum(new int[]{10, 15, 3, 7}, 99));
        Assertions.assertFalse(hasGivenSum(new int[]{10, 15, 3, 7}, 0));
        Assertions.assertTrue(hasGivenSum(new int[]{18, 3, 0, -3}, 0));

        Assertions.assertTrue(hasGivenSum2(new int[]{10, 15, 3, 7}, 17));
        Assertions.assertFalse(hasGivenSum2(new int[]{10, 15, 3, 7}, 99));
        Assertions.assertFalse(hasGivenSum2(new int[]{10, 15, 3, 7}, 0));
        Assertions.assertTrue(hasGivenSum2(new int[]{18, 3, 0, -3}, 0));

        final Random random = new Random();

        final int iterations = 100;
        final long[] checkTimes = new long[iterations];
        final long[] bonusTimes = new long[iterations];
        for (int i = 0; i < iterations; i++) {
            final int[] numbers = random.ints(10000, 0, Integer.MAX_VALUE).toArray();
            final long totalCheck = random.nextLong();

            final long checkStart = System.currentTimeMillis();
            hasGivenSum(numbers, totalCheck);
            checkTimes[i] = System.currentTimeMillis() - checkStart;

            final long bonusStart = System.currentTimeMillis();
            hasGivenSum2(numbers, totalCheck);
            bonusTimes[i] = System.currentTimeMillis() - bonusStart;
        }

        System.out.println(LongStream.of(checkTimes).average());
        System.out.println(LongStream.of(bonusTimes).average());
    }

    private static boolean hasGivenSum(final int[] numbers, final long checkValue) {
        for (int a : numbers) {
            for (int b : numbers) {
                if (checkValue == a + b) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean hasGivenSum2(final int[] numbers, final long checkValue) {
        final List<Long> sums = new ArrayList<>();

        final List<Integer> history = new ArrayList<>();
        for (int number : numbers) {
            for (final int h : history) {
                sums.add((long) (h + number));
            }
            history.add(number);
        }

        return sums.contains(checkValue);
    }
}
