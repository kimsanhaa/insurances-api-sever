package contract.management.repository;

import contract.management.repository.dto.AddContract;
import contract.management.repository.dto.AddProductCollateral;
import contract.management.repository.dto.Collateral;
import contract.management.repository.dto.ContractInfo;

import java.util.List;


public interface ContractRepository {
    int addContract(AddContract addContract);
    List<Collateral> findCollaterals(int productId);
    void addProductCollateral(List<AddProductCollateral> addProductCollaterals);
    ContractInfo findContractInfo(int contractId);

}
