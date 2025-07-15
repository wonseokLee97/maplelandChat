package com.mapleland.chatservice.application.chat.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class XssSanitizerHelper {

    public String sanitize(String input) {
        // 1. < > 마킹
        String markedInput = input
                .replace("<", "__LT__")
                .replace(">", "__GT__");

        // 2. Jsoup 설정
        Safelist safelist = Safelist.none();
        safelist
                .addTags("img", "video", "source", "figcaption", "figure")
                .addAttributes("img", "src", "alt", "width", "height")
                .addAttributes("video", "src", "controls", "width", "height");

        Document.OutputSettings outputSettings = new Document.OutputSettings()
                .prettyPrint(false)
                .escapeMode(Entities.EscapeMode.xhtml);

        // 3. sanitize (마킹된 input 사용)
        String sanitized = Jsoup.clean(markedInput, "", safelist, outputSettings);

        // 4. 마킹 복원
        sanitized = sanitized.replace("__LT__", "<").replace("__GT__", ">");

        // 5. input과 비교 (비교도 마킹 후 비교)
        String originalMarked = input.replace("<", "__LT__").replace(">", "__GT__");

        if (!sanitized.equals(originalMarked)) {
            throw new IllegalArgumentException("적절하지 않은 메시지입니다.");
        }

        return sanitized;
    }
}
