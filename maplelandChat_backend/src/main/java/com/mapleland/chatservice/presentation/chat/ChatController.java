package com.mapleland.chatservice.presentation.chat;

import com.mapleland.chatservice.application.chat.ChatUseCase;
import com.mapleland.chatservice.presentation.chat.dto.ReportChatRequestDto;
import com.mapleland.chatservice.shared.api.ApiResponse;
import com.mapleland.chatservice.shared.type.http.HttpSuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class ChatController {

    private final ChatUseCase chatUseCase;

    @GetMapping
    public ApiResponse<?> getChatListByCursor(
            @RequestParam("itemId") String itemId,
            @RequestParam("limit") int limit,
            @RequestParam("cursor") String cursor,
            @RequestParam("search") String search
    ) {

        return ApiResponse.success(
                chatUseCase.getChat(itemId, limit, cursor, search),
                HttpSuccessType.SUCCESS_GET_CHAT_LIST
        );
    }

    @PostMapping("/report")
    public ApiResponse<?> reportChat(@RequestBody ReportChatRequestDto requestDto) {
        chatUseCase.reportChat(requestDto);
        return ApiResponse.success(
                null,
                HttpSuccessType.SUCCESS_REPORT_CHAT
        );
    }
}
