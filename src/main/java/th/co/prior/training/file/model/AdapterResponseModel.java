package th.co.prior.training.file.model;

import lombok.Data;

@Data
public class AdapterResponseModel {
    private int code;
    private SaleTransactionModel message;
}
