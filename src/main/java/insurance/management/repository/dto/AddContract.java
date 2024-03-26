package insurance.management.repository.dto;

import insurance.management.constants.CONTRACT_STATUS;
import insurance.management.controller.dto.SaveInsurance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddContract {
    private int contractId;
    private int productId;
    private int status;
    private String contractStartDate;
    private String contractEndDate;
    private int period;
    private float totalPremium;
    List<Integer> collaterals;


    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getContractId() {
        return contractId;
    }

    public static AddContract of(SaveInsurance saveInsurance,int status, String startDate,String endDate,float totalPremium){
        return  AddContract.builder()
                .productId(saveInsurance.getProductId())
                .status(status)
                .contractStartDate(startDate)
                .contractEndDate(endDate)
                .period(saveInsurance.getPeriod())
                .totalPremium(totalPremium)
                .collaterals(saveInsurance.getCollaterals())
                .build();
    }
}
