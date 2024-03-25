package insurance.management.repository.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
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

    /**
     * 1. useGeneratedKeys="true"
     * 2. keyProperty="변수명"
     * 3. keyColumn="Sql컬럼명"
     * 출처: https://msource.tistory.com/218 [MLog:티스토리]
     */
}
