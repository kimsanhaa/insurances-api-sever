package contract.management.repository.dto;

import lombok.Getter;

@Getter
public class ModifyContract {
    private int contractId;
    private int period;
    private int status;
    private float totalPremium;

    public ModifyContract(int contractId, int period, int status, float totalPremium) {
        this.contractId = contractId;
        this.period = period;
        this.status = status;
        this.totalPremium = totalPremium;
    }
}
