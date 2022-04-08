package framework.apiserver.domain.board.like;

import framework.apiserver.core.security.jwt.JwtAuthService;
import framework.apiserver.core.security.jwt.JwtTokenProvider;
import framework.apiserver.core.security.jwt.dto.TokenDto;
import framework.apiserver.core.security.user.dto.UserDto;
import framework.apiserver.domain.board.Board;
import framework.apiserver.domain.board.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class LikeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtAuthService jwtAuthService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void setUp(){
    }

    @Test
    public void getLikeReturnOk() throws Exception {
        //GIVEN
        //Get Access Token
        UserDto userDto = new UserDto("admin","tester");
        TokenDto accessToken = jwtAuthService.login(userDto);

        //save board
        String loginId = jwtTokenProvider.parseClaims(accessToken.getAccessToken()).getSubject();

        Board board = new Board();
        board.setTitle("title");
        board.setContent("content");
        board.setCreatedBy(loginId);
        board = boardRepository.save(board);

        String boardId = board.getBoardId();

        //WHEN
        ResultActions resultActions =
                this.mvc.perform(get("/api/board/{boardId}/like", boardId)
                        .header("Authorization", "Bearer " + accessToken.getAccessToken()));

        //THEN
        resultActions
                .andExpect(status().isOk());
    }
}
