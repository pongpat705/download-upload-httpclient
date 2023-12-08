package th.co.prior.training.file.adapter;

import org.springframework.http.ResponseEntity;
import th.co.prior.training.file.model.AdapterResponseModel;
import th.co.prior.training.file.model.SaleTransactionModel;

public interface SaleTransactionAdapter {
    SaleTransactionModel getSaleTransactionModel(String userId);

    ResponseEntity<AdapterResponseModel> getSaleTransactionModelEntity(String userId);
}
