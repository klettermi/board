package mi.board.domain.board.comment.exception;

import mi.board.global.enums.ErrorCode;
import mi.board.global.exception.GlobalException;

public class CommentNotFoundException extends GlobalException {
  public CommentNotFoundException() {
    super(ErrorCode.COMMENT_NOT_FOUND);
  }
}
