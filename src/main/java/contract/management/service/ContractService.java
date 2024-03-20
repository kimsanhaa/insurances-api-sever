package contract.management.service;

import contract.management.constants.CONTRACT_STATUS;
import contract.management.controller.dto.SaveContract;
import contract.management.controller.dto.UpdateContract;
import contract.management.repository.ContractRepository;
import contract.management.repository.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import contract.management.service.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;

import static contract.management.service.utils.DateUtil.addPeriodToDate;
import static contract.management.service.utils.DateUtil.getNowDate;


@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

    public void saveContract(SaveContract saveContract){
        int status = CONTRACT_STATUS.CONTRACT_ACTIVE;
        String startDate = getNowDate();
        String endDate = addPeriodToDate(startDate,saveContract.getPeriod());

        List<Collateral> collaterals = contractRepository.findCollaterals(saveContract.getProductId());
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
        contractRepository.addContract(contract);
        int contractId = contract.getContractId();

        List<AddProductCollateral> AddProductCollaterals = new ArrayList<>();
        for(Collateral collateral : collaterals){
            if(saveContract.getCollaterals().contains(collateral.getId())){
                AddProductCollaterals.add(new AddProductCollateral(saveContract.getProductId(),contractId, collateral.getId(), true));
            }else{
                AddProductCollaterals.add(new AddProductCollateral(saveContract.getProductId(),contractId, collateral.getId(), false));
            }
        }
        contractRepository.addProductCollateral(AddProductCollaterals);
    }
    public ContractInfo findContractInfo(int contractId){
        return contractRepository.findContractInfo(contractId);
    }
    public void updateContract(UpdateContract updateContract){
        ContractInfo contractInfo = contractRepository.findContractInfo(updateContract.getContractId());
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
        new ModifyContract(updateContract.getContractId(), updateContract.getPeriod(), updateContract.getStatus(),calculateTotalPremium(updateProductCollaterals, updateContract.getPeriod()));
        contractRepository.updat();
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

