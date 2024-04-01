package insurance.management.controller.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UpdateInsurance {
    @NonNull
    private int productId;

    @NonNull
    private int contractId;
    private UpdateCollateral collateral;
    private UpdatePeriod period;
    private UpdateContract contract;

    public UpdateInsurance(int productId, int contractId, UpdateCollateral collateral, UpdatePeriod period, UpdateContract contract) {
        this.productId = productId;
        this.contractId = contractId;
        this.collateral = collateral;
        this.period = period;
        this.contract = contract;
    }

    public UpdateInsurance(int productId, int contractId, UpdateCollateral collateral) {
        this.productId = productId;
        this.contractId = contractId;
        this.collateral = collateral;
    }

    public UpdateInsurance(int productId, int contractId, UpdatePeriod period) {
        this.productId = productId;
        this.contractId = contractId;
        this.period = period;
    }
    public UpdateInsurance(int productId, int contractId, UpdateContract contract) {
        this.productId = productId;
        this.contractId = contractId;
        this.contract = contract;
    }
}
