package framework.apiserver.domain.board;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "A_BOARD")
@Data
public class Board {
    @Id
    @Column(name ="board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String boardId;

    @Column(name = "title")
    String title;

    @Column(name = "content")
    String content;

    @Column(name = "created_by")
    String createdBy;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    LocalDateTime creationDate;

    @Column(name = "modified_by")
    String modifiedBy;

    @UpdateTimestamp
    @Column(name = "modified_date")
    LocalDateTime modifiedDate;
}
