package framework.apiserver.domain.board.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/list/{pageNum}")
    public ResponseEntity<List<BookDto>> getBookList(@PathVariable int pageNum) {
        List<BookDto> result = bookService.getBookList(pageNum);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
