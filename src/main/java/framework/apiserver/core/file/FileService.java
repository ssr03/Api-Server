package framework.apiserver.core.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

public interface FileService {
    Resource loadFileAsResource(String fileName);

    @Transactional
    List<FileDto> uploadFiles(MultipartFile[] files);

    String getFileUri(String fileName);
}
