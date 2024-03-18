package contract.management.repository.dto;

public class AddProductCollateral {
    private int productId;
    private int contractId;
    private int collateralId;
    private boolean isSign;

    public AddProductCollateral(int productId, int contractId, int collateralId, boolean isSign) {
        this.productId = productId;
        this.contractId = contractId;
        this.collateralId = collateralId;
        this.isSign = isSign;
    }
}
