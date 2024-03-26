package insurance.management.service.dto;

import lombok.Getter;

@Getter
public class UpdateProductCollateral {
    private int productId;
    private int contractId;
    private int collateralId;
    private boolean isSign;

    public UpdateProductCollateral(int productId, int contractId, int collateralId, boolean isSign) {
        this.productId = productId;
        this.contractId = contractId;
        this.collateralId = collateralId;
        this.isSign = isSign;
    }
}
