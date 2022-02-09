package framework.apiserver.core.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="U_FILE")
@Data
public class Ufile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //원본파일명
    @Column(name = "original_name")
    String originalName;

    //저장파일명
    @Column(name = "stored_name")
    String storedName;

    //파일 타입
    @Column(name = "file_type")
    String fileType;

    //파일 확장자
    @Column(name = "file_ext")
    String fileExt;

    //용량(Byte)
    @Column(name = "file_amount")
    Long fileAmount;

    //썸네일
    @Column(name = "thumbnail")
    String thumbnail;

    //비고1
    @Column(name = "attribute_1")
    String attribute1;

    //비고2
    @Column(name = "attribute_2")
    String attribute2;

    //비고3
    @Column(name = "attribute_3")
    String attribute3;

    //비고4
    @Column(name = "attribute_4")
    String attribute4;

    //비고5
    @Column(name = "attribute_5")
    String attribute5;

    @Column(name = "created_by")
    String createdBy;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "creation_date", updatable = false)
    LocalDateTime creationDate;

    @Column(name = "modified_by")
    String modifiedBy;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_date")
    LocalDateTime modifiedDate;
}
