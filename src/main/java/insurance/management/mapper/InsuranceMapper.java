package insurance.management.mapper;

import contract.management.repository.dto.*;
import insurance.management.repository.dto.*;
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
    int updateProductCollateral(Collateral collateral);
}
