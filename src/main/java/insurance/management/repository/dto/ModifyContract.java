package insurance.management.repository.dto;

import lombok.Getter;

@Getter
public class ModifyContract {
    private int contractId;
    private int period;
    private String startDate;
    private String endDate;
    private int status;
    private float totalPremium;

    public ModifyContract(int contractId, int period, String startDate, String endDate, int status, float totalPremium) {
        this.contractId = contractId;
        this.period = period;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.totalPremium = totalPremium;
    }
}
