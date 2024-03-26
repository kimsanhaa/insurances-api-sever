package insurance.management.repository;

import insurance.management.repository.dto.*;
import insurance.management.service.dto.UpdateProductCollateral;

import java.util.List;


public interface InsuranceRepository {
    int addContract(AddContract addContract);
    List<Collateral> findCollaterals(int productId);
    void addProductCollateral(List<AddProductCollateral> addProductCollaterals);
    ContractInfo findContractInfo(int contractId);
    void updateProductCollaterals(List<UpdateProductCollateral> updateProductCollaterals);
    void updateContract(ModifyContract modifyContract);
}
