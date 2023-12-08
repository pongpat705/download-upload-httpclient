package th.co.prior.training.file.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.json.Jackson2CodecSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import th.co.prior.training.file.adapter.SaleTransactionAdapter;
import th.co.prior.training.file.adapter.SaleTransactionAdaptorImpl;
import th.co.prior.training.file.component.RestTemplateUtilsComponent;
import th.co.prior.training.file.component.ToolUtilsComponent;
import th.co.prior.training.file.exception.MyException;
import th.co.prior.training.file.model.*;

import java.io.*;
import java.util.List;

@Service
@Slf4j
public class FileService {
    private ToolUtilsComponent toolsComponent;
    private SaleTransactionAdapter saleTransactionAdapter;

    public FileService(ToolUtilsComponent toolsComponent
            , @Qualifier("saleTransactionAdaptorImpl") SaleTransactionAdapter saleTransactionAdapter) {
        this.toolsComponent = toolsComponent;
        this.saleTransactionAdapter = saleTransactionAdapter;
    }

    public ResponseModel<Void> keepFileToLocalPath(FileUploadModel fileUploadModel) {
        ResponseModel<Void> result = new ResponseModel<>();
        result.setCode(200);
        result.setMessage("...");
        try {
            MultipartFile multipartFile = fileUploadModel.getFile();

            String fileNameToWrite = "/home/pongpat/Documents/Fluke-Machine/drive-d/data/file/" + fileUploadModel.getUser() + "_" + multipartFile.getOriginalFilename();
            File fileToWrite = new File(fileNameToWrite);
            multipartFile.transferTo(fileToWrite);

            for (MultipartFile mf : fileUploadModel.getFiles()) {
                String mfNameToWrite = "/home/pongpat/Documents/Fluke-Machine/drive-d/data/file/" + fileUploadModel.getUser() + "_" + mf.getOriginalFilename();
                File mfToWrite = new File(mfNameToWrite);
                mf.transferTo(mfToWrite);
            }

        } catch (Exception e) {
            result.setCode(500);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    public ResponseModel<UrlEncodeModel> readValueFormUrlEncode(UrlEncodeModel urlEncodeModel) {
        ResponseModel<UrlEncodeModel> result = new ResponseModel<>();
        result.setCode(200);
        result.setMessage("...");
        result.setData(urlEncodeModel);
        return result;
    }

    private String getUserId(){
        //call adapter
        //default x
        return "x";
    }
    public void downloadFileFromLocal(String userId, HttpServletResponse response) throws IOException, FileNotFoundException {
        OutputStream outputStream = response.getOutputStream();
        File downloadFile = new File("/home/pongpat/Documents/Fluke-Machine/drive-d/data/file/Name_file_1.pdf");
        FileInputStream fileInputStream = new FileInputStream(downloadFile);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=userId" + userId + ".pdf");

        byte[] buffer = new byte[1024];
        int len;
        while ((len = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        outputStream.close();

    }

    public void generateCsv(String userId, HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=userId" + userId + ".csv");
            String rawData = "Name,Age,City\n" +
                    "John,25,New York\n" +
                    "Alice,30,San Francisco\n" +
                    "Bob,22,Los Angeles\n" +
                    "Emma,28,Chicago\n" +
                    "Daniel,35,Miami\n" +
                    "Olivia,26,Seattle\n" +
                    "Michael,31,Austin\n" +
                    "Sophia,29,Boston\n" +
                    "David,27,Dallas";
            String[] rawDataArray = rawData.split("\\n");

            for (int i = 0; i < rawDataArray.length; i++) {
                String d = rawDataArray[i];
                byte[] bytes = d.getBytes();
                outputStream.write(bytes);
                if (i % 5 == 0) {
                    log.info("flushed");
                    outputStream.flush();
                }
            }
            outputStream.flush();
        } catch (Exception e) {
            response.setContentType("application/json");
            ResponseModel<Void> result = new ResponseModel<>();
            result.setCode(500);
            result.setMessage(e.getMessage());
            ObjectMapper objectMapper = this.toolsComponent.getObjectMapper();
            try {
                byte[] buffer = objectMapper.writeValueAsBytes(result);
                if (null != outputStream) {
                    outputStream.write(buffer);
                    outputStream.flush();
                }
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    public ResponseModel<Void> writeCsv(String userId, List<EmployeeModel> employeeModels) {
        ResponseModel<Void> result = new ResponseModel<>();
        result.setCode(200);
        result.setMessage("...");
        try {
            File file = new File("/home/pongpat/Documents/Fluke-Machine/drive-d/data/file/" + userId + ".csv");
            FileOutputStream fos = new FileOutputStream(file);

            StringBuilder sb = new StringBuilder();
            sb.append("name|lastname|position|joindate").append(System.lineSeparator());
            String header = sb.toString();
            fos.write(header.getBytes());
            for (EmployeeModel e : employeeModels) {
                String detail = String.format("%s|%s|%s|%s" + System.lineSeparator(), e.getName(), e.getLastName(), e.getPosition(), e.getJoinDate());
                fos.write(detail.getBytes());
            }
            fos.flush();
            fos.close();

        } catch (Exception e) {
            result.setCode(500);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    public ResponseModel<SaleTransactionModel> getSaleTxn(String userId) {
        ResponseModel<SaleTransactionModel> result = new ResponseModel<>();
        result.setCode(200);
        result.setMessage("...");

        try {
            ResponseEntity<AdapterResponseModel> responseEntity = this.saleTransactionAdapter.getSaleTransactionModelEntity(userId);
            if (responseEntity.getStatusCode().is4xxClientError()) {
                result.setCode(responseEntity.getStatusCode().value());
                result.setMessage("client error");
            }
            result.setData(responseEntity.getBody().getMessage());
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage("get sale transaction failed " + e.getMessage());
        }

        return result;
    }
}
