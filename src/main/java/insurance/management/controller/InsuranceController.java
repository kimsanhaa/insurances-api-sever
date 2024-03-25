package insurance.management.controller;

import insurance.management.controller.dto.SaveContract;
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
    public ResponseEntity<Object> save(@RequestBody SaveContract contract){
        insuranceService.saveContract(contract);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<Object> find(@PathVariable int contractId){
        ContractInfo contractInfo = insuranceService.findContractInfo(contractId);
        return new ResponseEntity<Object>(contractInfo,HttpStatus.OK);
    }


}
