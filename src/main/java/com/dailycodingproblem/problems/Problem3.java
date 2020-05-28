package com.dailycodingproblem.problems;

import org.junit.jupiter.api.Assertions;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem3 {

    //Given the root to a binary tree, implement serialize(root), which serializes the tree into
    // a string, and deserialize(s), which deserializes the string back into the tree.
    //
    //For example, given the following Node class
    //
    //class Node:
    //    def __init__(self, val, left=None, right=None):
    //        self.val = val
    //        self.left = left
    //        self.right = right
    //The following test should pass:
    //
    //node = Node('root', Node('left', Node('left.left')), Node('right'))
    //assert deserialize(serialize(node)).left.left.val == 'left.left'

    // Node(
    //   'root',
    //   Node(
    //     'left',
    //     Node('left.left')
    //   ),
    //   Node('right')
    // )

    public static void main(String[] args) {
        Node node;
        String serialized;
        Node deserialized;

        node = new Node(
                "root",
                new Node(
                        "left",
                        new Node("left.left")
                ),
                new Node("right")
        );

        serialized = Node.serialize(node);
        deserialized = Node.deserialize(serialized);

        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals("left.left", deserialized.getLeft().getLeft().getVal());

        node = new Node(
                "root",
                new Node("left"),
                new Node(
                        "right",
                        new Node("leftier"),
                        new Node(
                                "right.right",
                                null,
                                null
                        )
                )
        );

        serialized = Node.serialize(node);
        deserialized = Node.deserialize(serialized);

        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals("right.right", deserialized.getRight().getRight().getVal());
    }

    static class Node {
        private final String val;
        private final Node left;
        private final Node right;

        Node(final String val, final Node left, final Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        Node(final String val, final Node left) {
            this(val, left, null);
        }

        Node(final String val) {
            this(val, null, null);
        }

        String getVal() {
            return val;
        }

        Node getLeft() {
            return left;
        }

        Node getRight() {
            return right;
        }

        static String serialize(final Node node) {
            if (Objects.isNull(node)) {
                return String.valueOf(node);
            }

            return String.format(
                    "['%s';%s;%s]",
                    node.getVal(),
                    Node.serialize(node.getLeft()),
                    Node.serialize(node.getRight())
            );
        }

        static Node deserialize(final String s) {
            if (s.equals("null")) {
                return null;
            }

            if (!s.startsWith("[") || !s.endsWith("]")) {
                throw new IllegalArgumentException();
            }

            final String root = s.replaceAll("^\\[", "").replaceAll("]$", "");

            final StringBuilder valueBuilder = new StringBuilder();
            final StringBuilder leftBuilder = new StringBuilder();
            final StringBuilder rightBuilder = new StringBuilder();

            final AtomicBoolean inValue = new AtomicBoolean(false);
            final AtomicBoolean inLeft = new AtomicBoolean(false);
            final AtomicBoolean inRight = new AtomicBoolean(false);

            final AtomicInteger depth = new AtomicInteger();

            for (int i = 0; i < root.length(); i++) {
                final String val = String.valueOf(root.charAt(i));

                if (val.equals("[")) {
                    depth.incrementAndGet();
                }

                if (val.equals("]")) {
                    depth.decrementAndGet();
                }

                if (depth.intValue() == 0) {
                    if (val.equals("'")) {
                        inValue.set(!inValue.get());
                        continue;
                    }

                    if (val.equals(";")) {
                        if (leftBuilder.length() == 0) {
                            inLeft.set(true);
                        } else {
                            inRight.set(true);
                        }
                        continue;
                    }
                }

                if (inRight.get()) {
                    rightBuilder.append(val);
                } else if (inLeft.get()) {
                    leftBuilder.append(val);
                } else if (inValue.get()) {
                    valueBuilder.append(val);
                }
            }

            return new Node(
                    valueBuilder.toString(),
                    Node.deserialize(leftBuilder.toString()),
                    Node.deserialize(rightBuilder.toString())
            );
        }
    }
}
