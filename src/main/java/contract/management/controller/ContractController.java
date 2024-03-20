package contract.management.controller;

import contract.management.controller.dto.SaveContract;
import contract.management.repository.dto.ContractInfo;
import contract.management.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContractController {
    private final ContractService contractService;

    @PostMapping("/contracts")
    public ResponseEntity<Object> save(@RequestBody SaveContract contract){
        contractService.saveContract(contract);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<Object> find(@PathVariable int contractId){
        ContractInfo contractInfo = contractService.findContractInfo(contractId);
        return new ResponseEntity<Object>(contractInfo,HttpStatus.OK);
    }

}
