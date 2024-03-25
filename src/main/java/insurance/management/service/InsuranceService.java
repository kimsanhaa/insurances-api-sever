package insurance.management.service;

import insurance.management.constants.CONTRACT_STATUS;
import insurance.management.controller.dto.ExpectedInsurance;
import insurance.management.controller.dto.SaveInsurance;
import insurance.management.controller.dto.UpdateInsurance;
import insurance.management.repository.InsuranceRepository;
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

    public void saveContract(SaveInsurance saveInsurance){
        int status = CONTRACT_STATUS.CONTRACT_ACTIVE;
        String startDate = getNowDate();
        String endDate = addPeriodToDate(startDate, saveInsurance.getPeriod());

        List<Collateral> collaterals = insuranceRepository.findCollaterals(saveInsurance.getProductId());
        List<Collateral> signCollaterals = new ArrayList<>();

        for(Collateral collateral : collaterals){
            if(saveInsurance.getCollaterals().contains(collateral.getId())){
                collateral.setSign(true);
                signCollaterals.add(collateral);
            }
        }
        float totalPremium = calculateTotalPremium(signCollaterals, saveInsurance.getPeriod());

        AddContract contract = AddContract.builder()
                .productId(saveInsurance.getProductId())
                .status(status)
                .contractStartDate(startDate)
                .contractEndDate(endDate)
                .totalPremium(totalPremium)
                .collaterals(saveInsurance.getCollaterals())
                .period(saveInsurance.getPeriod()).build();
        insuranceRepository.addContract(contract);
        int contractId = contract.getContractId();

        List<AddProductCollateral> AddProductCollaterals = new ArrayList<>();
        for(Collateral collateral : collaterals){
            if(saveInsurance.getCollaterals().contains(collateral.getId())){
                AddProductCollaterals.add(new AddProductCollateral(saveInsurance.getProductId(),contractId, collateral.getId(), true));
            }else{
                AddProductCollaterals.add(new AddProductCollateral(saveInsurance.getProductId(),contractId, collateral.getId(), false));
            }
        }
        insuranceRepository.addProductCollateral(AddProductCollaterals);
    }
    public ContractInfo findContractInfo(int contractId){
        return insuranceRepository.findContractInfo(contractId);
    }
    public void updateInsurance(UpdateInsurance updateInsurance){
        if(updateInsurance.getCollaterial().getIsUpdate()){
            updateCollaterial(updateInsurance);
        }
        if(updateInsurance.getPeriod().getIsUpdate()){
            updatePeriod(updateInsurance);
        }
        if(updateInsurance.getContract().getIsUpdate()){
            updateContract(updateInsurance);
        }
    }
    private void updateContract(UpdateInsurance updateInsurance){

    }
    private void updatePeriod(UpdateInsurance updateInsurance) {
        String startDate = getNowDate();
        String endDate = addPeriodToDate(startDate, updateInsurance.getPeriod().getPeriod());
        ModifyContract modifyContract = new ModifyContract(updateInsurance.getContractId(), updateInsurance.getPeriod(), startDate, endDate, updateInsurance.getStatus(), calculateTotalPremium(updateProductCollaterals, updateInsurance.getPeriod()));
        insuranceRepository.updateContract(modifyContract);
    }

    private void updateCollaterial(UpdateInsurance updateInsurance) {
        ContractInfo contractInfo = insuranceRepository.findContractInfo(updateInsurance.getContractId());
        //담보 비교
        List<Collateral> PreviousCollaterals = contractInfo.getCollaterals();
        List<Integer> inputCollaterals = updateInsurance.getCollaterial().getCollaterals();
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
    }
    public float expected(ExpectedInsurance expectedInsurance) {
        List<Collateral> collaterals = insuranceRepository.findCollaterals(expectedInsurance.getProductId());
        List<Collateral> signCollaterals = new ArrayList<>();

        for (Collateral collateral : collaterals) {
            if (expectedInsurance.getCollaterals().contains(collateral.getId())) {
                signCollaterals.add(collateral);
            }
        }
        return calculateTotalPremium(signCollaterals, expectedInsurance.getPeriod());
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

