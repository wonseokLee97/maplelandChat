package com.mapleland.chatservice.application.chat.helper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class XssSanitizerHelperTest {

    @Autowired
    private XssSanitizerHelper sanitizer;

    @Test
    void 허용된_기호는_예외없이_통과된다() {
//        assertDoesNotThrow(() -> sanitizer.sanitize("<3"));
        assertDoesNotThrow(() -> sanitizer.sanitize("괴력 << 귓 주세요"));
//        assertDoesNotThrow(() -> sanitizer.sanitize("<img src=\"hello.png\" width=\"100\" height=\"100\">"));
    }

    @Test
    void 스크립트태그는_예외를_던진다() {
        assertThrows(IllegalArgumentException.class, () -> {
            sanitizer.sanitize("<script>alert('xss')</script>");
        });
    }

    @Test
    void 허용되지_않은_img속성은_예외를_던진다() {
        assertThrows(IllegalArgumentException.class, () -> {
            sanitizer.sanitize("<img onclick=\"alert(1)\" src=\"evil.png\">");
        });
    }

    @Test
    void 잘못된_img형태는_예외를_던진다() {
        assertThrows(IllegalArgumentException.class, () -> {
            sanitizer.sanitize("<img src=hello.png>");
        });
    }
}