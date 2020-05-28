package com.dailycodingproblem.problems;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Problem17 {

    public static void main(String[] args) {
        Assertions.assertEquals(
                20,
                new Problem17().calculateLongestAbsolutePathLength(
                        "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"
                )
        );

        Assertions.assertEquals(
                32,
                new Problem17().calculateLongestAbsolutePathLength(
                        "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"
                )
        );

        Assertions.assertEquals(
                44,
                new Problem17().calculateLongestAbsolutePathLength(
                        "dir\n\tsub1\n\t\tsubsub1\n\t\t\tsubsubsub1\n\t\t\t\tfile1.ext\n\tlongbutshallowsubdirectoryname\n\t\tfile2.ext"
                )
        );

        Assertions.assertEquals(
                0,
                new Problem17().calculateLongestAbsolutePathLength(
                        "dir\n\tsubdir1\n\t\tsubdir2"
                )
        );

        Assertions.assertEquals(
                12,
                new Problem17().calculateLongestAbsolutePathLength("dir\nfile.ext")
        );
    }

    private int calculateLongestAbsolutePathLength(final String value) {
        final String[] pieces = value.split("\n");

        int longestAbsolutePathLength = 0;

        final List<String> currentDir = new ArrayList<>();

        for (final String piece : pieces) {
            if (isFile(piece)) {
                final int absolutePathLength = calculateAbsolutePathLength(currentDir, piece);
                if (absolutePathLength > longestAbsolutePathLength) {
                    longestAbsolutePathLength = absolutePathLength;
                }
            } else {
                final int depthDifference = currentDir.size() - calculateDepth(piece);
                for (int i = 0; i < depthDifference; i++) {
                    currentDir.remove(currentDir.size() - 1);
                }
                currentDir.add(stripIndent(piece));
            }
        }

        /*
        piece           depth   currentDir.size()   action
        --------------------------------------------------
        dir             0       0                   add
        \tsubdir1       1       1                   add
        \tsubdir2       1       2                   remove 1, add
        \t\tsubsubdir2  2       2                   add
        \tsubdir3       1       3                   remove 2, add
         */

        return longestAbsolutePathLength;
    }

    private static final Pattern FILE_PATTERN = Pattern.compile("\\..+");
    private boolean isFile(final String value) {
        return FILE_PATTERN.matcher(value).find();
    }

    private int calculateAbsolutePathLength(final List<String> currentDir, final String file) {
        return currentDir.stream().mapToInt(String::length).sum() + currentDir.size() + stripIndent(file).length();
    }

    private int calculateDepth(final String value) {
        return value.length() - stripIndent(value).length();
    }

    private String stripIndent(final String value) {
        return value.stripLeading();
    }
}
