package com.mapleland.chatservice.application.chat.helper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class XssSanitizerHelper {

    private static final Pattern TAG_PATTERN = Pattern.compile("<\\s*/?\\s*([a-zA-Z]+)[^>]*>");
    private static final Set<String> ALLOWED_TAGS = Set.of("img", "video", "source", "figcaption", "figure");
    private final Safelist safelist;

    public XssSanitizerHelper() {
        this.safelist = Safelist.none()
                .addTags("img", "video", "source", "figcaption", "figure")
                .addAttributes("img", "src", "alt", "width", "height")
                .addAttributes("video", "src", "controls", "width", "height");
    }

    public String sanitize(String input) {
        Document.OutputSettings outputSettings = new Document.OutputSettings()
                .prettyPrint(false)
                .escapeMode(Entities.EscapeMode.base);

        Matcher matcher = TAG_PATTERN.matcher(input);

        while (matcher.find()) {
            String tagName = matcher.group(1).toLowerCase();
            if (!ALLOWED_TAGS.contains(tagName)) {
                throw new IllegalArgumentException("적절하지 않은 메시지입니다.");
            }
        }

        String cleaned = Jsoup.clean(input, "", safelist, outputSettings);
        String unescapedCleaned = Parser.unescapeEntities(cleaned, false);

        if (!unescapedCleaned.equals(input)) {
            throw new IllegalArgumentException("적절하지 않은 메시지입니다.");
        }

        return input;
    }
}
