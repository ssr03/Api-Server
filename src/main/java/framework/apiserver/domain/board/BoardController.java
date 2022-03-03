package framework.apiserver.domain.board;

import framework.apiserver.domain.board.like.LikeDto;
import framework.apiserver.domain.board.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
@RequiredArgsConstructor
public class BoardController {
    private final LikeService likeService;

    @GetMapping("/{boardId}/like")
    public ResponseEntity<LikeDto> getLike(@PathVariable String boardId){
        LikeDto likeDto = likeService.getLike(boardId);
        return new ResponseEntity<>(likeDto, HttpStatus.OK);
    }

    @PostMapping("/{boardId}/like")
    public ResponseEntity<Long> updateLike(@PathVariable String boardId){
        long result = likeService.pushLike(boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/like")
    public ResponseEntity<Long> deleteLike(@PathVariable String boardId){
        long result = likeService.cancelLike(boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
