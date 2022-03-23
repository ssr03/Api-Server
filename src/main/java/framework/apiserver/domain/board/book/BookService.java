package framework.apiserver.domain.board.book;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BookService {
    List<BookDto> getBookList(int pageNum);
}
