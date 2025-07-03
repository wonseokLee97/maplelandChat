package com.mapleland.chatService.shared.type.http;

import com.mapleland.chatService.shared.type.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HttpSuccessType implements Type {

    // ============= [USER / 200: 요청 성공] =============
    SUCCESS_SIGN_UP(200, "회원가입 성공."),
    SUCCESS_SIGN_IN(200, "로그인 성공"),
    SUCCESS_LOGOUT(200, "로그아웃 성공"),
    SUCCESS_TOKEN_ISSUANCE(200, "토큰 발급 성공"),
    SUCCESS_TOKEN_REFRESH(200, "토큰 재발급 성공"),

    // ============= [CHAT / 200: 요청 성공] =============
    SUCCESS_GET_CHAT_LIST(200, "채팅 목록 조회 성공"),
    SUCCESS_REPORT_CHAT(200, "채팅 신고 성공"),

    // ============= [POST / 200: 요청 성공] =============
    SUCCESS_SAVE_POST(200, "게시글 작성 성공"),
    SUCCESS_GET_POST(200, "게시글 조회 성공"),
    SUCCESS_GET_POST_LIST(200, "게시물 리스트 조회 성공"),
    SUCCESS_DELETE_POST(200, "게시글 삭제 성공"),
    SUCCESS_UPDATE_POST(200, "게시글 수정 성공"),

    // ============= [COMMENT / 200: 요청 성공] =============
    SUCCESS_SAVE_COMMENT(200, "댓글 작성 성공"),
    SUCCESS_GET_COMMENT(200, "댓글 조회 성공"),
    SUCCESS_DELETE_COMMENT(200, "댓글 삭제 성공"),

    // ============= [LIKE / 200: 요청 성공] =============
    SUCCESS_SAVE_LIKE(200, "좋아요 성공"),
    SUCCESS_DELETE_LIKE(200, "좋아요 취소 성공"),
    SUCCESS_LIKE_COUNT(200, "좋아요 개수 확인 성공");

    private final int statusCode;
    private final String message;
}
