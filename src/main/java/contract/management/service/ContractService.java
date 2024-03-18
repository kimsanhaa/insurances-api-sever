package contract.management.service;

import contract.management.constants.CONTRACT_STATUS;
import contract.management.controller.dto.SaveContract;
import contract.management.repository.ContractRepository;
import contract.management.repository.dto.AddContract;
import contract.management.repository.dto.AddProductCollateral;
import contract.management.repository.dto.Collateral;
import contract.management.repository.dto.ContractInfo;
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
    public float calculateTotalPremium( List<Collateral> collaterals,int period){
        float total = 0;
        for(Collateral collateral : collaterals){
            total += collateral.getJoinAmount() / collateral.getBaseAmount();
        }
        total = period * total;

        total = MathUtil.round(total,2);
        return total;
    }
}

