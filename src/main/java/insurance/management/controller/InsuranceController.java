package insurance.management.controller;

import insurance.management.controller.dto.SaveInsurance;
import insurance.management.controller.dto.UpdateInsurance;
import insurance.management.repository.dto.ContractInfo;
import insurance.management.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InsuranceController {
    private final InsuranceService insuranceService;

    @PostMapping("/contracts")
    public ResponseEntity<Object> save(@RequestBody SaveInsurance contract){
        insuranceService.saveInsurance(contract);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<Object> find(@PathVariable int contractId){
        ContractInfo contractInfo = insuranceService.findContractInfo(contractId);
        return new ResponseEntity<Object>(contractInfo,HttpStatus.OK);
    }
    @PatchMapping("/contracts/{contractId}")
    public ResponseEntity<Object> update(@RequestBody UpdateInsurance updateInsurance){
        insuranceService.updateInsurance(updateInsurance);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
//    @GetMapping("/insurance/quote")
//    public ResponseEntity<Object> expectedQuote(@RequestBody ExpectedInsurance expectedInsurance){
//        float expected = insuranceService.expected(expectedInsurance);
//        return new ResponseEntity<Object>(expected,HttpStatus.OK);
//    }
}
