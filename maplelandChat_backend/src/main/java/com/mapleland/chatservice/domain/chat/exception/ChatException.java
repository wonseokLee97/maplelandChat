package com.mapleland.chatservice.domain.chat.exception;

public class ChatException extends RuntimeException {
    public ChatException(String message) {
        super(message);
    }

    public ChatException(String message, Throwable cause) {
      super(message, cause);
    }
  
    public static class ChatBlockedException extends ChatException {
      public ChatBlockedException(String message) {
        super(message);
      }
  
      public ChatBlockedException(String message, Throwable cause) {
        super(message, cause);
      }
    }

    public static class AlreadyReportedUserException extends ChatException {
        public AlreadyReportedUserException(String message) {
            super(message);
        }

        public AlreadyReportedUserException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class AlreadyBannedUserException extends ChatException {
        public AlreadyBannedUserException(String message) {
            super(message);
        }

        public AlreadyBannedUserException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
