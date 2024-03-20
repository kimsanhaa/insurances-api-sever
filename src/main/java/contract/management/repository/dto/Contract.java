package contract.management.repository.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Contract{
    private int contractId;
    private int productId;
    private String productName;
    private int contractPeriod;
    private String startDate;
    private String endDate;
    private float totalPremium;
    private int status;
}
