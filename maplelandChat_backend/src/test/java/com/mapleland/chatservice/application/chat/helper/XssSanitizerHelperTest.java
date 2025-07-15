package com.mapleland.chatservice.application.chat.helper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class XssSanitizerHelperTest {

    @Autowired
    private XssSanitizerHelper sanitizer;

    @Test
    void testValidAngleBrackets() {
        assertDoesNotThrow(() -> {
            String result = sanitizer.sanitize("<3");
            assertEquals("<3", result);
        });

        assertDoesNotThrow(() -> {
            String result = sanitizer.sanitize("<<");
            assertEquals("<<", result);
        });

        assertDoesNotThrow(() -> {
            String result = sanitizer.sanitize("<>");
            assertEquals("<>", result);
        });
    }

    @Test
    void testInvalidScriptTag() {
        assertThrows(IllegalArgumentException.class, () -> {
            sanitizer.sanitize("<script>alert(1)</script>");
        });
    }

    @Test
    void testValidImageTag() {
        assertDoesNotThrow(() -> {
            String result = sanitizer.sanitize("<img src=\"hello.png\" width=\"100\" height=\"100\">");
            assertTrue(result.contains("<img"));
        });
    }

    @Test
    void testInvalidImageTag_missingQuote() {
        assertThrows(IllegalArgumentException.class, () -> {
            sanitizer.sanitize("<img src=hello.png>");
        });
    }
}
