package insurance.management.controller.dto;

import lombok.Getter;

@Getter
public class UpdateInsurance {
    private int productId;
    private int contractId;
    private UpdateCollaterial collateral;
    private UpdatePeriod period;
    private UpdateContract contract;
}
