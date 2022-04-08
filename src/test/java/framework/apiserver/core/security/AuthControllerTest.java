package framework.apiserver.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import framework.apiserver.core.security.user.User;
import framework.apiserver.core.security.user.UserRepository;
import framework.apiserver.core.security.user.UserService;
import framework.apiserver.core.security.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.hamcrest.Matchers.containsString;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void loginWithWrongLoginPwReturnUnauthorized() throws Exception {
        //GIVEN
        UserDto userDto = new UserDto("admin","1");

        Gson gson = new Gson();
        String userInfo = gson.toJson(userDto);

        //WHEN
        ResultActions resultActions = this.mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userInfo));

        //THEN
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    public void loginWithLoginIdAndLoginPwReturnOk() throws Exception {
        //GIVEN
        String loginId = "admin";
        String loginPw = "tester";
        UserDto userDto = new UserDto(loginId, loginPw);

        Gson gson = new Gson();
        String userInfo = gson.toJson(userDto);

        //WHEN
        ResultActions resultActions = this.mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userInfo));

        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("accessToken")));
    }

    @Test
    public void signupWithAlreadyExistUserReturnNotFound() throws Exception {
        //Given
        String loginId = "admin";
        String loginPw = "1234";
        User user = new User();
        user.setLoginId(loginId);
        user.setLoginPw(loginPw);
        user.setName("관리자");

        Gson gson = new Gson();
        String userInfo = gson.toJson(user);

        //WHEN
        ResultActions resultActions = this.mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userInfo));

        //THEN
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    public void singupUserReturnOk() throws Exception {
        //Given
        String loginId = "tester1";
        String loginPw = "tester1";
        User user = new User();
        user.setLoginId(loginId);
        user.setLoginPw(loginPw);
        user.setName("테스터1");

        Gson gson = new Gson();
        String userInfo = gson.toJson(user);

        //WHEN
        ResultActions resultActions = this.mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userInfo));

        //THEN
        resultActions
                .andExpect(status().isOk());
    }
}
