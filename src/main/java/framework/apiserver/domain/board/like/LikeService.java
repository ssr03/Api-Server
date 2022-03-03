package framework.apiserver.domain.board.like;

public interface LikeService {
    long pushLike(String boardId);
    long cancelLike(String boardId);
    LikeDto getLike(String boardId);
}
