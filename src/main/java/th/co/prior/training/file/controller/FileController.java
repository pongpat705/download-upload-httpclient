package th.co.prior.training.file.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import th.co.prior.training.file.exception.MyException;
import th.co.prior.training.file.model.*;
import th.co.prior.training.file.service.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
public class FileController {


    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/api/upload/write/to-local")
    public ResponseModel<Void> uploadFileToLocal(@ModelAttribute FileUploadModel fileUploadModel){
        return this.fileService.keepFileToLocalPath(fileUploadModel);
    }

    @PostMapping("/api/form/urlencode")
    public ResponseModel<?> readValueFormUrlEncode(@ModelAttribute UrlEncodeModel urlEncodeModel){
         return this.fileService.readValueFormUrlEncode(urlEncodeModel);
    }

    @GetMapping("/api/download/{userId}/from-local")
    public void downloadFileFromLocal(@PathVariable String userId, HttpServletRequest request, HttpServletResponse response) throws IOException, FileNotFoundException {
        this.fileService.downloadFileFromLocal(userId, response);
    }

    @GetMapping("/api/download/{userId}/csv")
    public void downloadCsv(@PathVariable String userId, HttpServletRequest request, HttpServletResponse response){
        this.fileService.generateCsv(userId, response);
    }

    @GetMapping("/api/write/{userId}/csv")
    public ResponseModel<Void> writeCsv(@PathVariable String userId, @RequestBody List<EmployeeModel> employeeModels){
        return this.fileService.writeCsv(userId, employeeModels);
    }

    @GetMapping("/api/get/sale/{userId}/txn")
    public ResponseModel<SaleTransactionModel> getSaleTxn(@PathVariable String userId){
        return this.fileService.getSaleTxn(userId);
    }
}
