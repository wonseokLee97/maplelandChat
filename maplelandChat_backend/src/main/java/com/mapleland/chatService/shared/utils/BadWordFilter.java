package com.mapleland.chatService.shared.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class BadWordFilter {
    private Trie trie;
    private final Map<String, String> keywordToReplacement = new HashMap<>();

    @PostConstruct
    public void init() throws Exception {
        // JSON 파일 로딩
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getClassLoader().getResourceAsStream("curse.json");

        Map<String, List<String>> rawMap = mapper.readValue(is, new TypeReference<>() {});
        Trie.TrieBuilder builder = Trie.builder();

        // Trie 및 역매핑 구성
        for (Map.Entry<String, List<String>> entry : rawMap.entrySet()) {
            String replacement = entry.getKey();
            for (String keyword : entry.getValue()) {
                builder.addKeyword(keyword);
                keywordToReplacement.put(keyword, replacement);
            }
        }
        trie = builder.ignoreOverlaps().build();
    }

    public String filter(String input) {
        Collection<Emit> emits = trie.parseText(input);
        List<Emit> sorted = new ArrayList<>(emits);
        sorted.sort((a, b) -> b.getStart() - a.getStart()); // 뒤에서부터

        StringBuilder sb = new StringBuilder(input);
        for (Emit emit : sorted) {
            String keyword = emit.getKeyword();
            String replacement = keywordToReplacement.get(keyword);
            sb.replace(emit.getStart(), emit.getEnd() + 1, replacement);
        }
        return sb.toString();
    }
}

