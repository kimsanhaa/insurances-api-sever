package insurance.management.mapper;


import insurance.management.repository.dto.*;
import insurance.management.service.dto.UpdateProductCollateral;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InsuranceMapper {
    int addContract(AddContract addContract);
    int addProductCollateral(AddProductCollateral addProductCollateral);
    Product getProductByProductId(int productId);
    List<Collateral> getCollateralsByProductId(int productId);
    Contract getContractByContractId(int contractId);
    List<Collateral> getSignCollateralsByContractId(int contractId);
    int updateContract(ModifyContract modifyContract);
    int updateProductCollateral(UpdateProductCollateral collateral);
    int deleteProductCollateralByContractId(int contractId);
    int updateTotalPremiumByContractId(float totalPremium, int contractId);
    int updateTotalPremiumAndPeriodByContractId(float totalPremium, int period,int contractId);
    int updateContractStatusByContractId(int status, int contractId);
}
