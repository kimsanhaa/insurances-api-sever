package insurance.management.service;

import insurance.management.constants.CONTRACT_STATUS;
import insurance.management.controller.dto.SaveContract;
import insurance.management.controller.dto.UpdateContract;
import insurance.management.repository.InsuranceRepository;
import contract.management.repository.dto.*;
import insurance.management.repository.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import insurance.management.service.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;

import static insurance.management.service.utils.DateUtil.addPeriodToDate;
import static insurance.management.service.utils.DateUtil.getNowDate;


@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;

    public void saveContract(SaveContract saveContract){
        int status = CONTRACT_STATUS.CONTRACT_ACTIVE;
        String startDate = getNowDate();
        String endDate = addPeriodToDate(startDate,saveContract.getPeriod());

        List<Collateral> collaterals = insuranceRepository.findCollaterals(saveContract.getProductId());
        List<Collateral> signCollaterals = new ArrayList<>();

        for(Collateral collateral : collaterals){
            if(saveContract.getCollaterals().contains(collateral.getId())){
                collateral.setSign(true);
                signCollaterals.add(collateral);
            }
        }
        float totalPremium = calculateTotalPremium(signCollaterals,saveContract.getPeriod());

        AddContract contract = AddContract.builder()
                .productId(saveContract.getProductId())
                .status(status)
                .contractStartDate(startDate)
                .contractEndDate(endDate)
                .totalPremium(totalPremium)
                .collaterals(saveContract.getCollaterals())
                .period(saveContract.getPeriod()).build();
        insuranceRepository.addContract(contract);
        int contractId = contract.getContractId();

        List<AddProductCollateral> AddProductCollaterals = new ArrayList<>();
        for(Collateral collateral : collaterals){
            if(saveContract.getCollaterals().contains(collateral.getId())){
                AddProductCollaterals.add(new AddProductCollateral(saveContract.getProductId(),contractId, collateral.getId(), true));
            }else{
                AddProductCollaterals.add(new AddProductCollateral(saveContract.getProductId(),contractId, collateral.getId(), false));
            }
        }
        insuranceRepository.addProductCollateral(AddProductCollaterals);
    }
    public ContractInfo findContractInfo(int contractId){
        return insuranceRepository.findContractInfo(contractId);
    }
    public void updateContract(UpdateContract updateContract){
        ContractInfo contractInfo = insuranceRepository.findContractInfo(updateContract.getContractId());
        //담보 비교
        List<Collateral> PreviousCollaterals = contractInfo.getCollaterals();
        List<Integer> inputCollaterals = updateContract.getCollaterals();
        List<Collateral> updateProductCollaterals = new ArrayList<>();

        for(Collateral collateral : PreviousCollaterals){
            int collateralId = collateral.getId();
            if(inputCollaterals.contains(collateralId) && collateral.isSign()){
                collateral.setSign(true);
                updateProductCollaterals.add(collateral);
            }else if(!inputCollaterals.contains(collateralId) && collateral.isSign()){
                collateral.setSign(false);
                updateProductCollaterals.add(collateral);
            }
        }

        //계약 기간 변경 계약 상태 변경
        String startDate = getNowDate();
        String endDate = addPeriodToDate(startDate,updateContract.getPeriod());
        ModifyContract modifyContract = new ModifyContract(updateContract.getContractId(), updateContract.getPeriod(), startDate, endDate, updateContract.getStatus(), calculateTotalPremium(updateProductCollaterals, updateContract.getPeriod()));
        insuranceRepository.updateContract(modifyContract);
    }
    public float calculateTotalPremium(List<Collateral> collaterals,int period){
        float total = 0;
        for(Collateral collateral : collaterals){
            total += collateral.isSign() ? collateral.getJoinAmount() / collateral.getBaseAmount() : 0;
        }
        total = period * total;

        total = MathUtil.round(total,2);
        return total;
    }
}

