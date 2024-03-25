package insurance.management.repository;

import insurance.management.repository.dto.AddContract;
import insurance.management.repository.dto.AddProductCollateral;
import insurance.management.repository.dto.Collateral;
import insurance.management.repository.dto.ContractInfo;

import java.util.List;


public interface InsuranceRepository {
    int addContract(AddContract addContract);
    List<Collateral> findCollaterals(int productId);
    void addProductCollateral(List<AddProductCollateral> addProductCollaterals);
    ContractInfo findContractInfo(int contractId);
}
