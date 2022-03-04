package framework.apiserver.domain.board.like;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LikeService {
    Flux<Object> pushLike(String boardId);
    Flux<Object> cancelLike(String boardId);
    Mono<LikeDto> getLike(String boardId);
}
