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

    @Value("/api/file/")
    private String IMG_API;

    private String THUMBNAIL_PREFIX ="thumbnail-";

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

    /**
     * 파일업로드
     * @param files
     */
    @Override
    public List<FileDto> uploadFiles(MultipartFile[] files) {
        List<FileDto> list = new ArrayList<>();

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

            } catch (IOException e) {
                e.printStackTrace();
                throw new FileException("파일 " + fileName + "을 저장할 수 없습니다. 다시 시도해 보세요.");
            }

            /*
             * Insert Data to U_FILE Table
             * */
            FileDto fileDto = new FileDto();
            fileDto.setFileName(fileName);
            fileDto.setStoredName(storedName);
            fileDto.setThumbNail("thumbnail-"+ storedName);
            fileDto.setExt(ext);
            fileDto.setFileAmount(fileSize);
            list.add(fileDto);
        }

        return list;
    }

    /**
     * Thumbnail업로드
     * @param file
     */
    @Override
    public FileDto uploadThumbnail(MultipartFile file) {
        String uploadFileName =file.getOriginalFilename();
        //IE has file path
        uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(uploadFileName));
        String ext = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

        String storedName = String.valueOf(UUID.randomUUID()).concat(ext);

        if(fileName.contains("..")) {
            throw new FileException("파일명에 허용되지 않는 문자가 포함되어 있습니다." + fileName);
        }

        if(ext.equals(".pdf") || ext.equals(".tif") || ext.equals(".tiff") ||
                ext.equals(".xls") || ext.equals(".xlsx") || ext.equals(".doc") || ext.equals(".docx")) {
            throw new FileException(ext +"형식은 썸네일로 지정할 수 없습니다.");
        }
        /*
         * create Thumbnail image
         * */
        try {
            Path targetLocation = this.fileStorageLocation.resolve(storedName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            Thumbnails.of(new java.io.File(String.valueOf(targetLocation)))
                    .size(37, 35)
                    .toFile(new java.io.File(String.valueOf(this.fileStorageLocation.resolve(THUMBNAIL_PREFIX +storedName))));

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("파일 " + fileName + "을 저장할 수 없습니다. 다시 시도해 보세요.");
        }

        FileDto fileDto = new FileDto();
        fileDto.setFileName(fileName);
        fileDto.setThumbNail(THUMBNAIL_PREFIX + storedName);
        fileDto.setExt(ext);
        return fileDto;
    }

    @Override
    public String getFileUri(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(IMG_API)
                .path(fileName)
                .toUriString();
    }
}
