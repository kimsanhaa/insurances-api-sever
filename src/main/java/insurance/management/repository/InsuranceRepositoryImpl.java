package insurance.management.repository;

import insurance.management.mapper.InsuranceMapper;
import insurance.management.repository.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        if(contract == null && signCollaterals.size() == 0){
            return null;
        }
        return new ContractInfo(contract,signCollaterals);
    }

    @Override
    public int deleteProductCollateral(int contractId) {
        return insuranceMapper.deleteProductCollateralByContractId(contractId);
    }

    @Override
    public int updateTotalPremium(float totalPremium, int contractId) {
        return insuranceMapper.updateTotalPremiumByContractId(totalPremium,contractId);
    }
    @Override
    public int updateTotalPremiumAndPeriod(float totalPremium, int period, int contractId) {
        return insuranceMapper.updateTotalPremiumAndPeriodByContractId(totalPremium,period,contractId);
    }

    @Override
    public int updateContractStatus(int status, int contractId) {
        return insuranceMapper.updateContractStatusByContractId(status,contractId);
    }

    @Override
    public Product findProduct(int productId) {
        return insuranceMapper.getProductByProductId(productId);
    }
}
