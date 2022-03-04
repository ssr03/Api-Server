package framework.apiserver.domain.board.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private String boardId;
    private String loginId;
    private Long likeCnt;
    private boolean isSelected;
}
