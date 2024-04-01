package insurance.management.service;

import insurance.management.controller.dto.*;
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
        List<Integer> actualCollaterals = getActualCollaterals(actual);

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

    @Test
    public void sut_기간만료된_보험은_업데이트_요청시_예외가_발생한다(){
        //Arrange
        InsuranceService sut = insuranceService;
        RuntimeException actual = null;
        UpdateInsurance updateInsurance = new UpdateInsurance(1,1,null,null,null);

        //Act
        try {
            sut.updateInsurance(updateInsurance);
        }catch (RuntimeException e){
            actual  = e;
        }

        //Assert
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(Exception.class);
    }

    @Test
    public void sut는_담보를_업데이트한다(){
        //Arrange
        InsuranceService sut = insuranceService;
        List<Integer> expectedCollaterals = Arrays.asList(3);
        int expectedPremium = 217105;
        UpdateCollateral uc = new UpdateCollateral(true, expectedCollaterals);
        int contractId = 57;
        int productId = 2;
        UpdateInsurance updateInsurance = new UpdateInsurance(productId,contractId,uc);

        //act
        sut.updateInsurance(updateInsurance);

        //Assert
        ContractInfo actual = sut.findContractInfo(contractId);
        List<Integer> actualCollaterals = getActualCollaterals(actual);
        float actualPremium= actual.getContract().getTotalPremium();

        Assertions.assertThat(actualCollaterals).isEqualTo(expectedCollaterals);
        Assertions.assertThat(actualPremium).isEqualTo(expectedPremium);
    }

    @Test
    public void sut는_period를_업데이트한다(){
        //Arrange
        InsuranceService sut = insuranceService;
        float expectedPremium = 19736.8F;
        int expectedPeriod = 1;
        UpdatePeriod up = new UpdatePeriod(true,expectedPeriod);
        int contractId = 57;
        int productId = 2;
        UpdateInsurance updateInsurance = new UpdateInsurance(productId,contractId,up);

        //act
        sut.updateInsurance(updateInsurance);

        //Assert
        ContractInfo actual = sut.findContractInfo(contractId);
        float actualPremium= actual.getContract().getTotalPremium();

        Assertions.assertThat(actualPremium).isEqualTo(expectedPremium);
        Assertions.assertThat(actual.getContract().getContractPeriod()).isEqualTo(expectedPeriod);
    }

    @Test
    public void sut는_period_업데이트시_상태가_기간만료이면_예외가_발생한다(){
        //Arrange
        InsuranceService sut = insuranceService;
        int expectedPeriod = 1;
        UpdatePeriod up = new UpdatePeriod(true,expectedPeriod);
        int contractId = 1;
        int productId = 1;
        RuntimeException actual = null;
        UpdateInsurance updateInsurance = new UpdateInsurance(productId,contractId,up);

        //act
        try {
            sut.updateInsurance(updateInsurance);
        }catch (RuntimeException e){
            actual = e;
        }

        //Assert
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual).isInstanceOf(RuntimeException.class);
    }
    @Test
    public void sut는_보험료를_예상한다(){
        //Arrange
        InsuranceService sut = insuranceService;
        ExpectedInsurance expectedInsurance = new ExpectedInsurance(2,11, Arrays.asList(3,4));
        float expectedPremium =  648855.25f;

        //Act
        float actual = sut.expected(expectedInsurance);

        //Assert
        Assertions.assertThat(actual).isEqualTo(expectedPremium);
    }

    private static SaveInsurance setSaveInsurance(int productId, int period, int collateral1, int collateral2) {
        List<Integer> collaterals = Arrays.asList(collateral1,collateral2);
        return new SaveInsurance(productId,period,collaterals);
    }
    private static List<Integer> getActualCollaterals(ContractInfo actual) {
        List<Integer> actualCollaterals = new ArrayList<>();
        for(Collateral collateral : actual.getCollaterals()){
            actualCollaterals.add(collateral.getId());
        }
        return actualCollaterals;
    }
}