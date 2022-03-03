package framework.apiserver.domain.board.like;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeDto {
    private String boardId;
    private String loginId;
    private Long likeCnt;
    private boolean isSelected;
}
