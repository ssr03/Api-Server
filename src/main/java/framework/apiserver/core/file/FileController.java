package framework.apiserver.core.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> loadFile(@PathVariable String fileName
            , HttpServletRequest request) throws UnsupportedEncodingException {

        String contentType = null;
        Resource resource = null;
        String originalFileName = null;

        try {
            resource = fileService.loadFileAsResource(fileName);
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            originalFileName = resource.getFile().getName();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        HttpHeaders headers = new HttpHeaders();

        if(contentType == "application/pdf"){
            contentType += "; charset=UTF-8";
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\"");

        }else{
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\"");
        }

        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .headers(headers)
                .body(resource);
    }
}
