package framework.apiserver.domain.board.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/book")
public class BookController {
    private final BookService bookService;

    /**
     * Subject:
     * Description:
     * */
    @GetMapping("/list")
    public ResponseEntity<List<BookDto>> getBookList(@RequestParam(name="pageNum") int pageNum,
                                                     @RequestParam(name="pageSize", required = false, defaultValue = "10") int pageSize) {
        List<BookDto> result = bookService.getBookList(pageNum, pageSize);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
