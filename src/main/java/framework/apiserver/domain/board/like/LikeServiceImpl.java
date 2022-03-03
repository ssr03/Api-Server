package framework.apiserver.domain.board.like;

import framework.apiserver.core.error.exception.EntityNotFoundException;
import framework.apiserver.core.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService{
    private final RedisTemplate<String, String> redisTemplate;
    private final Util util;

    @Override
    public long pushLike(String boardId) {
        String loginId = util.getLoginId();

        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        Set<String> likes = setOperations.members(boardId);
        if(likes.contains(loginId))throw new EntityNotFoundException(loginId + "는 해당 게시물에 이미 좋아요를 눌렀습니다.");
        long result = setOperations.add(boardId, loginId);

        return result;
    }

    @Override
    public long cancelLike(String boardId) {
        String loginId = util.getLoginId();
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        long result = setOperations.remove(boardId, loginId);
        return result;
    }

    @Override
    public LikeDto getLike(String boardId) {
        String loginId = util.getLoginId();
        LikeDto likeDto = new LikeDto();
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        Set<String> likes = setOperations.members(boardId);

        likeDto.setLikeCnt(likes.stream().count());
        likeDto.setBoardId(boardId);
        likeDto.setLoginId(loginId);
        likeDto.setSelected(likes.contains(loginId));
        return likeDto;
    }
}
