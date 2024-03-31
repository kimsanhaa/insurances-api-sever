package insurance.management.service;

import insurance.management.controller.dto.SaveInsurance;
import insurance.management.repository.dto.Collateral;
import insurance.management.repository.dto.ContractInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class InsuranceServiceTest {

    @Autowired
    InsuranceService insuranceService;

    @Test
    public void sut는_계약을_저장하고_저장된_contractId로_저장된데이터를_검증한다(){
        //Arrange
        InsuranceService sut = insuranceService;
        SaveInsurance saveInsurance = setSaveInsurance(2,11,3,4);
        int expectedProductId = saveInsurance.getProductId();
        int expectedPeriod = saveInsurance.getPeriod();
        float expectedPremium =  648855;

        //Act
        int contractId = sut.saveInsurance(saveInsurance);

        //Assert
        ContractInfo actual = sut.findContractInfo(contractId);
        List<Integer> actualCollaterals = new ArrayList<>();
        for(Collateral collateral : actual.getCollaterals()){
            actualCollaterals.add(collateral.getId());
        }

        Assertions.assertThat(actual.getContract().getProductId()).isEqualTo(expectedProductId);
        Assertions.assertThat(actual.getContract().getContractPeriod()).isEqualTo(expectedPeriod);
        Assertions.assertThat(actualCollaterals).isEqualTo(saveInsurance.getCollaterals());
        Assertions.assertThat(actual.getContract().getTotalPremium()).isEqualTo(expectedPremium);
    }

    @Test
    @Transactional
    public void sut는_saveInsurance_의_period값이_제품_계약기간_보다_크면_예외가_발생한다(){
        //Arrange
        InsuranceService sut = insuranceService;
        SaveInsurance saveInsurance = setSaveInsurance(1,99,3,4);
        RuntimeException actual = null;

        //Act
        try {
            sut.saveInsurance(saveInsurance);
        }catch (RuntimeException e){
            actual  = e;
        }

        //Assert
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(Exception.class);
    }








    private static SaveInsurance setSaveInsurance(int productId, int period, int collateral1, int collateral2) {
        List<Integer> collaterals = Arrays.asList(collateral1,collateral2);
        return new SaveInsurance(productId,period,collaterals);
    }
}