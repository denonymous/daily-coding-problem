package com.dailycodingproblem.problems;

import org.junit.jupiter.api.Assertions;

public class Problem2 {

    //Given an array of integers, return a new array such that each element at index i of the
    // new array is the product of all the numbers in the original array except the one at i.
    //
    //For example, if our input was [1, 2, 3, 4, 5], the expected output would be
    // [120, 60, 40, 30, 24]. If our input was [3, 2, 1], the expected output would be [2, 3, 6].
    //
    //Follow-up: what if you can't use division?

    public static void main(final String[] args) {
        final long startCheck = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            Assertions.assertArrayEquals(
                    new int[]{120, 60, 40, 30, 24},
                    exclusivelyMultiply(new int[]{1, 2, 3, 4, 5})
            );

            Assertions.assertArrayEquals(
                    new int[]{2, 3, 6},
                    exclusivelyMultiply(new int[]{3, 2, 1})
            );
        }

        System.out.println(System.nanoTime() - startCheck);

        final long startCheck2 = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            Assertions.assertArrayEquals(
                    new int[]{120, 60, 40, 30, 24},
                    exclusivelyMultiply2(new int[]{1, 2, 3, 4, 5})
            );

            Assertions.assertArrayEquals(
                    new int[]{2, 3, 6},
                    exclusivelyMultiply2(new int[]{3, 2, 1})
            );
        }

        System.out.println(System.nanoTime() - startCheck2);
    }

    private static int[] exclusivelyMultiply(final int[] numbers) {
        final int[] ret = new int[numbers.length];

        for (int a = 0; a < numbers.length; a++) {
            int product = 1;

            for (int b = 0; b < numbers.length; b++) {
                if (a != b) {
                    product = product * numbers[b];
                }
            }

            ret[a] = product;
        }

        return ret;
    }

    private static int[] exclusivelyMultiply2(final int[] numbers) {
        final int[] ret = new int[numbers.length];

        int product = 1;
        for (int i : numbers) {
            product *= i;
        }

        for (int i = 0; i < numbers.length; i++) {
            ret[i] = product / numbers[i];
        }

        return ret;
    }
}
