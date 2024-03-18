package contract.management.repository;

import contract.management.constants.CONTRACT_STATUS;
import contract.management.repository.dto.AddContract;
import contract.management.repository.dto.Collateral;
import contract.management.repository.dto.ContractInfo;
import contract.management.repository.dto.ProductWithCollateral;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContractRepositoryImplTest {
    @Autowired
    ContractRepositoryImpl contractRepository;

    @Test
    public void test(){
       int contractId = 33;
        ContractInfo contractInfo = contractRepository.findContractInfo(contractId);
        Assertions.assertThat(contractInfo).isNotNull();
    }
}