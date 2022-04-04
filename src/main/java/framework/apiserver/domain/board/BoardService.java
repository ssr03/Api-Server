package framework.apiserver.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Page<BoardDto> getBoardList(Pageable pageable);
    Board saveBoard(BoardDto boardDto);
    BoardDto getBoard(String boardId);
}
