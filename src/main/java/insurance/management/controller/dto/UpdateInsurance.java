package insurance.management.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateInsurance {
    private int contractId;
    private UpdateCollaterial collaterial;
    private UpdatePeriod period;
    private UpdateContract contract;
}
