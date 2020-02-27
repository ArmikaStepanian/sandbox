package com.stepanian.sandbox;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTest {

    @Test
    void myTest() {
        assertTrue(Pattern.matches("[0-9]+", "5895"));
        assertTrue(Pattern.matches("[^0-9]+", "xhgf"));
        assertTrue(Pattern.matches("[0-9a-zA-Z]+", "fvkfBHYjvkfv98956"));
        assertTrue(Pattern.matches("[abc]", "a"));
        assertTrue(Pattern.matches("[abc][vn]", "cv"));
        assertTrue(Pattern.matches("[^abc]+", "jop5"));
    }
}
