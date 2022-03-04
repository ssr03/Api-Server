package framework.apiserver.domain.board;

import framework.apiserver.domain.board.like.LikeDto;
import framework.apiserver.domain.board.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
@RequiredArgsConstructor
public class BoardController {
    private final LikeService likeService;

    @GetMapping("/{boardId}/like")
    public ResponseEntity<Mono<LikeDto>> getLike(@PathVariable String boardId){
        Mono<LikeDto> likeDto = likeService.getLike(boardId);
        return new ResponseEntity<>(likeDto, HttpStatus.OK);
    }

    @PostMapping("/{boardId}/like")
    public ResponseEntity<Flux<Object>> updateLike(@PathVariable String boardId){
        Flux<Object> result = likeService.pushLike(boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/like")
    public ResponseEntity<Flux<Object>> deleteLike(@PathVariable String boardId){
        Flux<Object> result = likeService.cancelLike(boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
