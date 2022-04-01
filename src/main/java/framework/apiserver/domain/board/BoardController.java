package framework.apiserver.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * Board List 조회
     */
    @GetMapping("/")
    public ResponseEntity<Page<BoardDto>> getBoardList(Pageable pageable){
        Page<BoardDto> result = boardService.getBoardList(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Board 저장
     */
    @PostMapping("/")
    public ResponseEntity<Board> saveBoard(@RequestPart BoardDto board,
                                           @RequestPart(value ="file", required = false)MultipartFile file){
        board.setFile(file);
        Board result = boardService.saveBoard(board);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Board 상세 조회
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable String boardId){
        BoardDto result = boardService.getBoard(boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
