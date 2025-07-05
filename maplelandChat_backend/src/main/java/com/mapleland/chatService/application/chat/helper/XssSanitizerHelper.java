package com.mapleland.chatService.application.chat.helper;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class XssSanitizerHelper {

    public String sanitize(String input) {
        Safelist safelist = Safelist.basic(); // 기본적인 Safelist

        safelist.addTags("img", "video", "source", "figcaption", "figure");
        safelist.addAttributes("img", "src", "alt", "width", "height");
        safelist.addAttributes("video", "src", "controls", "width", "height");

        String sanitized = Jsoup.clean(input, safelist);

        if (!sanitized.equals(input)) {
            throw new IllegalArgumentException("적절하지 않은 메시지입니다.");
        }

        return sanitized;
    }
}
