package insurance.management.repository.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class ContractInfo  {
    private Contract contract;
    private List<Collateral> collaterals;

    public ContractInfo(Contract contract, List<Collateral> collaterals) {
        this.contract = contract;
        this.collaterals = collaterals;
    }

    @Override
    public String toString() {
        return "ContractInfo{" +
                "contract=" + contract +
                ", collaterals=" + collaterals +
                '}';
    }
}
