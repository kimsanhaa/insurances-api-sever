package insurance.management.service;

import insurance.management.controller.dto.ExpectedInsurance;
import insurance.management.controller.dto.SaveInsurance;
import insurance.management.controller.dto.UpdateInsurance;
import insurance.management.repository.dto.ContractInfo;

public interface InsuranceService {
    int saveInsurance(SaveInsurance contract);
    ContractInfo findContractInfo(int contractId);
    void updateInsurance(UpdateInsurance updateInsurance);
    float expected(ExpectedInsurance expectedInsurance);
}

