package insurance.management.repository;

import insurance.management.mapper.InsuranceMapper;
import contract.management.repository.dto.*;
import insurance.management.repository.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InsuranceRepositoryImpl implements InsuranceRepository {
    @Autowired
    private InsuranceMapper insuranceMapper;

    @Override
    public int addContract(AddContract addContract) {
        return insuranceMapper.addContract(addContract);
    }

    @Override
    public List<Collateral> findCollaterals(int productId) {
        return insuranceMapper.getCollateralsByProductId(productId);
    }
    @Override
    public void addProductCollateral(List<AddProductCollateral> productCollaterals) {
        for(AddProductCollateral productCollateral : productCollaterals) {
            insuranceMapper.addProductCollateral(productCollateral);
        }
    }
    @Override
    public ContractInfo findContractInfo(int contractId) {
        Contract contract = insuranceMapper.getContractByContractId(contractId);
        List<Collateral> signCollaterals = insuranceMapper.getSignCollateralsByContractId(contractId);
        return new ContractInfo(contract,signCollaterals);
    }
    public void updateContract(){

    }
}
