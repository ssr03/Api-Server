package framework.apiserver.domain.board;

import framework.apiserver.core.file.FileDto;
import framework.apiserver.core.file.FileService;
import framework.apiserver.core.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final Util util;

    @Override
    public Page<BoardDto> getBoardList(Pageable pageable) {
        return null;
    }

    @Override
    public Board saveBoard(BoardDto boardDto) {
        String loginId = util.getLoginId();

        Board board = new Board();

        //Thumbnail 저장
        Optional.ofNullable(boardDto.getFile()).ifPresent(file -> {
            if(!file.isEmpty()){
                FileDto fileDto = fileService.uploadThumbnail(boardDto.getFile());
                board.setThumbnail(fileDto.getThumbNail());
            }
        });

        //Board 저장
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setCreatedBy(loginId);
        board.setModifiedBy(loginId);
        Board result = boardRepository.save(board);
        return result;
    }

    @Override
    public BoardDto getBoard(String boardId) {
        return null;
    }
}
