package insurance.management.controller.dto;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class ExpectedInsurance {
    @NonNull
    private int productId;

    @NonNull
    private int period;

    @NonNull
    private List<Integer> collaterals;

    public ExpectedInsurance(int productId, int period, List<Integer> collaterals) {
        this.productId = productId;
        this.period = period;
        this.collaterals = collaterals;
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
