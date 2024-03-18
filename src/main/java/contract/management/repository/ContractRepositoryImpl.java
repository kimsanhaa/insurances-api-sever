package contract.management.repository;

import contract.management.mapper.ContractMapper;
import contract.management.repository.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractRepositoryImpl implements ContractRepository{
    @Autowired
    private ContractMapper contractMapper;

    @Override
    public int addContract(AddContract addContract) {
        return contractMapper.addContract(addContract);
    }

    @Override
    public List<Collateral> findCollaterals(int productId) {
        return contractMapper.getCollateralsByProductId(productId);
    }
    @Override
    public void addProductCollateral(List<AddProductCollateral> productCollaterals) {
        for(AddProductCollateral productCollateral : productCollaterals) {
            contractMapper.addProductCollateral(productCollateral);
        }
    }
    @Override
    public ContractInfo findContractInfo(int contractId) {
        Contract contract = contractMapper.getContractByContractId(contractId);
        List<Collateral> signCollaterals = contractMapper.getSignCollateralsByContractId(contractId);
        return new ContractInfo(contract,signCollaterals);
    }
}
