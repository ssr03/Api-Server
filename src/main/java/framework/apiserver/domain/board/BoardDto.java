package framework.apiserver.domain.board;

import framework.apiserver.core.security.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto implements Serializable {
    private static final String BOARD_ID = "board_id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String THUMBNAIL = "thumbnail";
    private static final String LOGIN_ID = "login_Id";
    private static final String NAME = "name";
    private static final String ROLE_CD = "role_cd";
    private static final String CREATION_DATE = "creation_date";
    private static final String MODIFIED_DATE = "modified_date";

    String boardId;
    String title;
    String content;
    String thumbnailPath;
    String loginId;
    String name;
    String roleCd;
    LocalDateTime creationDate;
    LocalDateTime modifiedDate;

    User user;
    MultipartFile file;

    public BoardDto(Map<String, Object> map){
        this.boardId = String.valueOf(Optional.ofNullable(map.get(BOARD_ID)).orElse(""));
        this.title = String.valueOf(Optional.ofNullable(map.get(TITLE)).orElse(""));
        this.content = String.valueOf(Optional.ofNullable(map.get(CONTENT)).orElse(""));
        this.thumbnailPath = String.valueOf(Optional.ofNullable(map.get(THUMBNAIL)).orElse(""));
        this.loginId= String.valueOf(Optional.ofNullable(map.get(LOGIN_ID)).orElse(""));
        this.name = String.valueOf(Optional.ofNullable(map.get(NAME)).orElse(""));
        this.roleCd = String.valueOf(Optional.ofNullable(map.get(ROLE_CD)).orElse(""));
        this.creationDate = (LocalDateTime)Optional.ofNullable(map.get(CREATION_DATE)).orElse(LocalDateTime.now());
        this.modifiedDate = (LocalDateTime)Optional.ofNullable(map.get(MODIFIED_DATE)).orElse(LocalDateTime.now());
    }
}
