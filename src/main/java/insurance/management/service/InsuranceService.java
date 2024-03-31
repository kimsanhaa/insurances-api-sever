package insurance.management.service;

import insurance.management.constants.CONTRACT_STATUS;
import insurance.management.controller.dto.ExpectedInsurance;
import insurance.management.controller.dto.SaveInsurance;
import insurance.management.controller.dto.UpdateInsurance;
import insurance.management.repository.InsuranceRepository;
import insurance.management.repository.dto.*;
import insurance.management.service.dto.SignCollateral;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import insurance.management.service.utils.MathUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static insurance.management.service.utils.DateUtil.addPeriodToDate;
import static insurance.management.service.utils.DateUtil.getNowDate;


@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;

    public void saveInsurance(SaveInsurance saveInsurance){
        List<SignCollateral> signCollaterals = getSignCollaterals(saveInsurance.getCollaterals(),saveInsurance.getProductId());

        float totalPremium = calculateTotalPremium(signCollaterals, saveInsurance.getPeriod());
        AddContract ac = saveContract(saveInsurance, totalPremium,saveInsurance.getPeriod());

        int contractId = ac.getContractId();
        saveProductCollateral(signCollaterals,saveInsurance.getProductId(),contractId);
    }

    private AddContract saveContract(SaveInsurance saveInsurance, float totalPremium, int period) {
        String startDate = getNowDate();
        String endDate = addPeriodToDate(startDate, period);
        AddContract ac = AddContract.of(saveInsurance, CONTRACT_STATUS.CONTRACT_ACTIVE, startDate, endDate, totalPremium);
        insuranceRepository.addContract(ac);
        return ac;
    }

    public ContractInfo findContractInfo(int contractId){
        return insuranceRepository.findContractInfo(contractId);
    }
    public void updateInsurance(UpdateInsurance updateInsurance){
        //  todo history 등록 service 추가
        ContractInfo contractInfo = insuranceRepository.findContractInfo(updateInsurance.getContractId());
        if(updateInsurance.getCollateral().getIsUpdate()){
            updateCollateral(
                    updateInsurance.getCollateral().getCollateralIds(),
                    updateInsurance.getProductId(),
                    updateInsurance.getContractId(),
                    contractInfo.getContract().getPeriod());
        }
        if(updateInsurance.getPeriod().getIsUpdate()){
            updatePeriod(
                    updateInsurance.getCollateral().getCollateralIds(),
                    updateInsurance.getProductId(),
                    updateInsurance.getContractId(),
                    updateInsurance.getPeriod().getPeriod());
        }
        if(updateInsurance.getContract().getIsUpdate()){
            updateContract(contractInfo.getContract().getStatus(),updateInsurance.getContract().getStatus(),updateInsurance.getContractId());
        }
    }
    @Transactional
    public void updateCollateral(List<Integer> collateralIds,int productId,int contractId,int period) {
        List<SignCollateral> signCollaterals = getSignCollaterals(collateralIds, productId);
        insuranceRepository.deleteProductCollateral(contractId);
        saveProductCollateral(signCollaterals,productId,contractId);

        float totalPremium = calculateTotalPremium(signCollaterals, period);
        insuranceRepository.updateTotalPremium(totalPremium,contractId);
    }

    @Transactional
    public void updatePeriod(List<Integer> collateralIds,int productId,int contractId,int period) {
        List<SignCollateral> signCollaterals = getSignCollaterals(collateralIds, productId);
        float totalPremium = calculateTotalPremium(signCollaterals, period);
        insuranceRepository.updateTotalPremiumAndPeriod(totalPremium,period,contractId);
    }
    private void updateContract(int previousStatus, int newStatus, int contractId){
        if(previousStatus == CONTRACT_STATUS.PERIOD_EXPIRED){
            throw new RuntimeException();
        }
        insuranceRepository.updateContractStatus(newStatus,contractId);
    }


    public float expected(ExpectedInsurance expectedInsurance) {
        List<SignCollateral> signCollaterals = getSignCollaterals(expectedInsurance.getCollaterals(),expectedInsurance.getProductId());
        return calculateTotalPremium(signCollaterals,expectedInsurance.getPeriod());
    }

    public float calculateTotalPremium(List<SignCollateral> scs,int period){
        float total = 0;
        for(SignCollateral sc : scs){
            total += sc.getJoinAmount() / sc.getBaseAmount();
        }
        total = period * total;

        total = MathUtil.round(total,2);
        return total;
    }
    private List<SignCollateral> getSignCollaterals(List<Integer> collateralIds, int productId) {
        List<Collateral> collaterals = insuranceRepository.findCollaterals(productId);
        List<SignCollateral> signCollaterals = collaterals.stream()
                .filter(collateral -> collateralIds.contains(collateral))
                .map(SignCollateral::of).
                collect(Collectors.toList());
        return signCollaterals;
    }
    private void saveProductCollateral( List<SignCollateral> signCollaterals,int productId,int contractId) {
        List<AddProductCollateral> pc = signCollaterals.stream()
                .map(sc -> new AddProductCollateral(productId, contractId, sc.getId()))
                .collect(Collectors.toList());
        insuranceRepository.addProductCollateral(pc);
    }
}

