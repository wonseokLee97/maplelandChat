package com.mapleland.chatService.shared.type.http;

import com.mapleland.chatService.shared.type.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HttpErrorType implements Type {

    // 400 BAD_REQUEST: 잘못된 요청
    INVALID_PASSWORD(400, "비밀번호 길이는 4자 이상 20자 이하여야 합니다."),
    INVALID_TITLE(400, "제목은 공백일 수 없습니다."),
    INVALID_CONTENT(400, "내용은 공백일 수 없습니다."),
    MALFORMED_TOKEN(400, "내용은 공백일 수 없습니다."),
    INVALID_PARENT_COMMENT(400, "삭제된 댓글에는 대댓글을 작성할 수 없습니다."),
    ALREADY_REPORTED(400, "이미 신고한 사용자입니다."),
    ALREADY_BANNED(400, "이미 제재당한 유저입니다."),

    // 401 UNAUTHORIZED: 인증 실패
    INVALID_TOKEN(401, "유효하지 않은 토큰."),
    EXPIRED_TOKEN(401, "만료된 토큰."),
    NO_TOKEN(401, "토큰이 존재하지 않습니다."),

    // 404 NOT_FOUND: 못 찾음
    NOT_FOUND_USER(404, "사용자를 찾지 못했습니다."),
    NOT_FOUND_POST(404, "게시물을 찾지 못했습니다."),
    NOT_FOUND_COMMENT(404, "댓글을 찾지 못했습니다."),
    NOT_FOUND_LIKE(404, "취소할 좋아요를 찾지 못했습니다."),
    NOT_FOUND_SOMETHING(404, "요청한 항목을 찾지 못했습니다."),

    // 403 FORBIDDEN: 권한 없음
    ACCESS_DENIED_UPDATE(403, "작성자만 수정할 수 있습니다."),
    ACCESS_DENIED_DELETE(403, "작성자만 삭제할 수 있습니다."),
    ACCESS_DENIED(403, "접근이 제한되었습니다."),

    // 409 CONFLICT: 충돌 발생
    DUPLICATE_USER_NAME(409, "중복된 아이디입니다."),
    DATABASE_CONSTRAINT_VIOLATION(409, "DB 제약조건 위반."),
    ALREADY_CREATED_LIKE(409, "이미 좋아요한 게시물입니다."),

    // 410 GONE: 없는 리소스 접근, 다시 요청 X
    ALREADY_DELETED_COMMENT(410, "이미 삭제한 댓글입니다."),

    // 423 TOO_MANY_REQUESTS
    TOO_MANY_REQUESTS(429, "요청이 과다합니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버 오류");

    private final int statusCode;
    private final String message;
}

