package insurance.management.service;

import insurance.management.constants.CONTRACT_STATUS;
import insurance.management.controller.dto.ExpectedInsurance;
import insurance.management.controller.dto.SaveInsurance;
import insurance.management.controller.dto.UpdateInsurance;
import insurance.management.repository.InsuranceRepository;
import insurance.management.repository.dto.*;
import insurance.management.service.dto.UpdateProductCollateral;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import insurance.management.service.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Collateral> signCollaterals = collaterals.stream()
                .filter(collateral -> saveInsurance.getCollaterals().contains(collateral))
                .collect(Collectors.toList());
        float totalPremium = calculateTotalPremium(signCollaterals, saveInsurance.getPeriod());
        AddContract ac= AddContract.of(saveInsurance, CONTRACT_STATUS.CONTRACT_ACTIVE, startDate, endDate, totalPremium);
        insuranceRepository.addContract(ac);

        int contractId = ac.getContractId();
        List<AddProductCollateral> productCollaterals = signCollaterals.stream()
                .map(collateral -> new AddProductCollateral(saveInsurance.getProductId(), contractId, collateral.getId()))
                .collect(Collectors.toList());
        insuranceRepository.addProductCollateral(productCollaterals);

    }
    public ContractInfo findContractInfo(int contractId){
        return insuranceRepository.findContractInfo(contractId);
    }
    public void updateInsurance(UpdateInsurance updateInsurance){
//        ContractInfo contractInfo = insuranceRepository.findContractInfo(updateInsurance.getContractId());
//        if(updateInsurance.getCollateral().getIsUpdate()){
//            updateCollateral(updateInsurance);
//        }
//        if(updateInsurance.getPeriod().getIsUpdate()){
//            updatePeriod(updateInsurance);
//        }
//        if(updateInsurance.getContract().getIsUpdate()){
//            updateContract(updateInsurance);
//        }
    }
    private void updateContract(UpdateInsurance updateInsurance){

    }
    private void updatePeriod(UpdateInsurance updateInsurance) {

    }

    private void updateCollateral(UpdateInsurance updateInsurance,List<Collateral> sellCollaterals) {


    }
//    private List<Collateral> signCheckCollateral(List<Collateral> sellCollaterals,List<Integer> signCollateralIds){
//
//    }
//    public float expected(ExpectedInsurance expectedInsurance) {
//
//    }

    public float calculateTotalPremium(List<Collateral> collaterals,int period){
        float total = 0;
        for(Collateral collateral : collaterals){
            total += collateral.getJoinAmount() / collateral.getBaseAmount();
        }
        total = period * total;

        total = MathUtil.round(total,2);
        return total;
    }
}

