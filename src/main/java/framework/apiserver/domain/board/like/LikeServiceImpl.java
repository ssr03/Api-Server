package framework.apiserver.domain.board.like;

import framework.apiserver.core.util.Util;
import framework.apiserver.domain.board.exception.LikeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveSetOperations;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService{
    private final ReactiveRedisOperations<String, String> redisOperations;
    private final Util util;

    @Override
    public Flux<Object> pushLike(String boardId) {
        String loginId = util.getLoginId();
        ReactiveSetOperations<String, String> operations = redisOperations.opsForSet();

        return operations
                .members(boardId).filter(member->loginId.equals(member))
                .flatMap(s -> Mono.error(new LikeException(loginId + "는 해당 게시물에 이미 좋아요를 눌렀습니다.")))
                .switchIfEmpty(operations.add(boardId,loginId));
    }

    @Override
    public Flux<Object> cancelLike(String boardId) {
        String loginId = util.getLoginId();
        ReactiveSetOperations<String, String> operations = redisOperations.opsForSet();

        Flux<Object> result = operations
                .members(boardId).filter(member->loginId.equals(member))
                .switchIfEmpty(Mono.error(new LikeException("이미 취소 되었습니다.")))
                .flatMap(s -> operations.remove(boardId,s));

        return result;
    }

    @Override
    public Mono<LikeDto> getLike(String boardId) {
        String loginId = util.getLoginId();

        return redisOperations.opsForSet().members(boardId)
                .collectList()
                .map(list->new LikeDto(boardId, loginId, list.stream().count(), list.contains(loginId)));
    }
}
