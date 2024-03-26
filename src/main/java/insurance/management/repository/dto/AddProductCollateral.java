package insurance.management.repository.dto;

import java.util.List;

public class AddProductCollateral {
    private int productId;
    private int contractId;
    private int collateralId;

    public AddProductCollateral(int productId, int contractId, int collateralId) {
        this.productId = productId;
        this.contractId = contractId;
        this.collateralId = collateralId;
    }
}
