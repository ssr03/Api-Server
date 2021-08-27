package framework.apiserver.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
@Configuration
public class FileConfig {

    @Value("${file.upload-dir}")
    String uploadDir;
}
