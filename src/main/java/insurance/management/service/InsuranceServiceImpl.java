package insurance.management.service;

import insurance.management.config.exception.BusinessException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static insurance.management.service.utils.DateUtil.addPeriodToDate;
import static insurance.management.service.utils.DateUtil.getNowDate;


@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService{
    private final InsuranceRepository insuranceRepository;
    @Override
    public int saveInsurance(SaveInsurance saveInsurance){
        Product product = insuranceRepository.findProduct(saveInsurance.getProductId());
        if(product.getPeriod()<saveInsurance.getPeriod()){
            throw new BusinessException("period 범위를 벗어났습니다.");
        }

        List<SignCollateral> signCollaterals = getSignCollaterals(saveInsurance.getCollaterals(),saveInsurance.getProductId());

        float totalPremium = calculateTotalPremium(signCollaterals, saveInsurance.getPeriod());
        AddContract ac = saveContract(saveInsurance, totalPremium,saveInsurance.getPeriod());

        int contractId = ac.getContractId();
        saveProductCollateral(signCollaterals,saveInsurance.getProductId(),contractId);
        return contractId;
    }

    @Override
    public ContractInfo findContractInfo(int contractId){
        return Optional.ofNullable(insuranceRepository.findContractInfo(contractId)).orElseThrow( ()-> new BusinessException("조회된 결과가 없습니다. 유효한 값을 입력하십시오."));
    }
    @Override
    public void updateInsurance(UpdateInsurance updateInsurance){
        ContractInfo contractInfo = insuranceRepository.findContractInfo(updateInsurance.getContractId());
        if(contractInfo.getContract().getStatus() == CONTRACT_STATUS.PERIOD_EXPIRED){
            throw new BusinessException("기간만료된 보험입니다.");
        }

        if(updateInsurance.getCollateral() != null && updateInsurance.getCollateral().getIsUpdate()){
            updateCollateral(
                    updateInsurance.getCollateral().getCollateralIds(),
                    updateInsurance.getProductId(),
                    updateInsurance.getContractId(),
                    contractInfo.getContract().getContractPeriod());
        }
        if(updateInsurance.getPeriod() != null && updateInsurance.getPeriod().getIsUpdate()){
            if(contractInfo.getContract().getContractPeriod()<updateInsurance.getPeriod().getPeriod()){
                throw new BusinessException("period 범위를 벗어났습니다.");
            }
            updatePeriod(
                    getCollateralIds(contractInfo.getCollaterals()),
                    updateInsurance.getProductId(),
                    updateInsurance.getContractId(),
                    updateInsurance.getPeriod().getPeriod());
        }
        if(updateInsurance.getContract() != null && updateInsurance.getContract().getIsUpdate()){
            updateContract(updateInsurance.getContract().getStatus(),updateInsurance.getContractId());
        }
    }

    @Override
    public float expected(ExpectedInsurance expectedInsurance) {
        List<SignCollateral> signCollaterals = getSignCollaterals(expectedInsurance.getCollaterals(),expectedInsurance.getProductId());
        return calculateTotalPremium(signCollaterals,expectedInsurance.getPeriod());
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
    private void updateContract(int newStatus, int contractId){
        insuranceRepository.updateContractStatus(newStatus,contractId);
    }
    private AddContract saveContract(SaveInsurance saveInsurance, float totalPremium, int period) {
        String startDate = getNowDate();
        String endDate = addPeriodToDate(startDate, period);
        AddContract ac = AddContract.of(saveInsurance, CONTRACT_STATUS.CONTRACT_ACTIVE, startDate, endDate, totalPremium);
        insuranceRepository.addContract(ac);
        return ac;
    }

    public float calculateTotalPremium(List<SignCollateral> scs, int period){
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
        validateCollateralForProduct(collaterals,collateralIds);
        List<SignCollateral> signCollaterals = collaterals.stream()
                .filter(collateral -> collateralIds.contains(collateral.getId()))
                .map(SignCollateral::of).
                collect(Collectors.toList());
        return signCollaterals;
    }

    private void validateCollateralForProduct(List<Collateral> allCollaterals, List<Integer> signCollateralIds){
        List<Integer> allCollateralIds = allCollaterals.stream().map(Collateral::getId).collect(Collectors.toList());
        if(!allCollateralIds.containsAll(signCollateralIds)){
            throw new BusinessException("해당 제품에 맞는 담보가 아닙니다.");
        };
    }
    private void saveProductCollateral(List<SignCollateral> signCollaterals,int productId,int contractId) {
        List<AddProductCollateral> pc = signCollaterals.stream()
                .map(sc -> new AddProductCollateral(productId, contractId, sc.getId()))
                .collect(Collectors.toList());
        insuranceRepository.addProductCollateral(pc);
    }
    private static List<Integer> getCollateralIds(List<Collateral> collaterals) {
        List<Integer> collateralIds = new ArrayList<>();
        for(Collateral collateral : collaterals){
            collateralIds.add(collateral.getId());
        }
        return collateralIds;
    }
}

