package framework.apiserver.domain.board.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    String recomNo;
    String drCode;
    String drCodeName;
    String recomtitle;
    String recomauthor;
    String recompublisher;
    String mokchFilePath;
    String recomisbn;
}
