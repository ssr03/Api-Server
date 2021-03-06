package framework.apiserver.core.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    String fileName;
    String storedName;
    String thumbNail;
    String ext;
    Long fileAmount;
}
