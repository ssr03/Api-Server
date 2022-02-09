package framework.apiserver.core.file;

import framework.apiserver.core.file.Exception.FileException;
import framework.apiserver.core.file.Exception.FileNotFoundException;
import framework.apiserver.core.util.Util;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService{
    private final Path fileStorageLocation;
    private final Util util;
    private final FileRepository fileRepository;

    @Value("/api/file/")
    private String IMG_API;

    @Override
    public Resource loadFileAsResource(String fileName) {
        Path filePath = fileStorageLocation.resolve(fileName).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if(!resource.exists()){
                throw new FileNotFoundException(fileName + " 을 찾을 수 없습니다.");
            }

            return resource;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new FileNotFoundException(fileName+ " 을 찾을 수 없습니다.");
        }
    }

    @Override
    public List<Ufile> uploadFiles(MultipartFile[] files) {
        List<Ufile> list = new ArrayList<>();

        String loginId = util.getLoginId();

        for(MultipartFile file: files){
            String uploadFileName =file.getOriginalFilename();
            //IE has file path
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(uploadFileName));
            String ext = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            Long fileSize = file.getSize();

            UUID uuid = UUID.randomUUID();
            String storedName = uuid.toString() + ext;

            if(fileName.contains("..")) {
                throw new FileException("파일명에 허용되지 않는 문자가 포함되어 있습니다." + fileName);
            }

            /*
             * File IO on Server
             * */
            try {
                Path targetLocation = this.fileStorageLocation.resolve(storedName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                if(!ext.equals(".pdf") && !ext.equals(".tif") && !ext.equals(".tiff") &&
                        !ext.equals(".xls") && !ext.equals(".xlsx") && !ext.equals(".doc") && !ext.equals(".docx")) {
                    /*
                     * create Thumbnail image
                     * */
                    Thumbnails.of(new java.io.File(String.valueOf(targetLocation)))
                            .size(37, 35)
                            .toFile(new java.io.File(String.valueOf(this.fileStorageLocation.resolve("thumbnail-" +storedName))));
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new FileException("파일 " + fileName + "을 저장할 수 없습니다. 다시 시도해 보세요.");
            }

            /*
             * Insert Data to U_FILE Table
             * */
            Ufile uf = new Ufile();
            uf.setOriginalName(fileName);
            uf.setStoredName(storedName);
            uf.setFileExt(ext);
            uf.setFileAmount(fileSize);
            uf.setThumbnail("thumbnail-" +storedName);
            uf.setCreatedBy(loginId);
            uf.setModifiedBy(loginId);

            list.add(uf);
        }

        fileRepository.saveAll(list);

        return list;
    }

    @Override
    public String getFileUri(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(IMG_API)
                .path(fileName)
                .toUriString();
    }
}
