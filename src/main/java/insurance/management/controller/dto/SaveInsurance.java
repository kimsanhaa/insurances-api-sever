package insurance.management.controller.dto;

import lombok.Getter;

import java.util.List;
@Getter
public class SaveInsurance {
    private int productId;
    private int period;
    private List<Integer> collaterals;
    @Override
    public String toString() {
        return "SaveContract{" +
                "productId=" + productId +
                ", period=" + period +
                ", collaterals=" + collaterals +
                '}';
    }

    public SaveInsurance(int productId, int period, List<Integer> collaterals) {
        this.productId = productId;
        this.period = period;
        this.collaterals = collaterals;
    }

    public SaveInsurance() {
    }
}
