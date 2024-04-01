package insurance.management.controller;

import insurance.management.controller.dto.ExpectedInsurance;
import insurance.management.controller.dto.SaveInsurance;
import insurance.management.controller.dto.UpdateInsurance;
import insurance.management.repository.dto.ContractInfo;
import insurance.management.service.InsuranceService;
import insurance.management.service.InsuranceServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class InsuranceController {
    private final InsuranceService insuranceService;
    @Autowired
    public InsuranceController(InsuranceServiceImpl insuranceService) {
        this.insuranceService = insuranceService;
    }
    @PostMapping("/insurances")
    public ResponseEntity<Object> save(@RequestBody @Validated SaveInsurance contract){
        insuranceService.saveInsurance(contract);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/insurances/{contractId}")
    public ResponseEntity<Object> find(@PathVariable int contractId){
        ContractInfo contractInfo = insuranceService.findContractInfo(contractId);
        return new ResponseEntity<Object>(contractInfo,HttpStatus.OK);
    }
    @PatchMapping("/insurances/{contractId}")
    public ResponseEntity<Object> update(@RequestBody @Validated UpdateInsurance updateInsurance){
        insuranceService.updateInsurance(updateInsurance);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
    @GetMapping("/insurance/quote")
    public ResponseEntity<Object> expectedQuote(@RequestBody @Validated ExpectedInsurance expectedInsurance){
        float expected = insuranceService.expected(expectedInsurance);
        return new ResponseEntity<Object>(expected,HttpStatus.OK);
    }
}
