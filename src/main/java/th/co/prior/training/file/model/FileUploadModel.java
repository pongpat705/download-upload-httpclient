package th.co.prior.training.file.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadModel {
    private MultipartFile file;
    private MultipartFile[] files;
    private String user;
}
