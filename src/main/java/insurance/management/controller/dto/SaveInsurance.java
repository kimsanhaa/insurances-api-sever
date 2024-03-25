package insurance.management.controller.dto;

import java.util.List;

public class SaveInsurance {
    private int productId;
    private int period;
    private List<Integer> collaterals;

    public int getProductId() {
        return productId;
    }

    public int getPeriod() {
        return period;
    }

    public List<Integer> getCollaterals() {
        return collaterals;
    }

    @Override
    public String toString() {
        return "SaveContract{" +
                "productId=" + productId +
                ", period=" + period +
                ", collaterals=" + collaterals +
                '}';
    }
}
