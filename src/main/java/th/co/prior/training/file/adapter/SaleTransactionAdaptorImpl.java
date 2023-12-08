package th.co.prior.training.file.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import th.co.prior.training.file.model.AdapterResponseModel;
import th.co.prior.training.file.model.SaleTransactionModel;

import java.util.HashMap;
import java.util.Map;

@Component("saleTransactionAdaptorImpl")
@Slf4j
public class SaleTransactionAdaptorImpl implements SaleTransactionAdapter{

    @Override
    public SaleTransactionModel getSaleTransactionModel(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = System.getenv("APP_CONFIG_ADAPTER_SALETRANSACTION");
        Map<String, String> userMap = new HashMap<>();
        userMap.put("userId", userId);
        AdapterResponseModel adapterResponseModel = restTemplate.postForObject(url, userMap, AdapterResponseModel.class);
        SaleTransactionModel callingResponse = adapterResponseModel.getMessage();

        return callingResponse;
    }

    @Override
    public ResponseEntity<AdapterResponseModel> getSaleTransactionModelEntity(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = System.getenv("APP_CONFIG_ADAPTER_SALETRANSACTION");

        Map<String, String> userMap = new HashMap<>();
        userMap.put("userId", userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(userMap, headers);

        ResponseEntity<AdapterResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, AdapterResponseModel.class);

        return response;
    }
}
