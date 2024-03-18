package contract.management.repository.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Contract{
    private int contractId;
    private String productName;
    private int contractPeriod;
    private String startDate;
    private String endDate;
    private float totalPremium;
    private int status;

    @Override
    public String toString() {
        return "Contract{" +
                "contractId=" + contractId +
                ", productName='" + productName + '\'' +
                ", contractPeriod=" + contractPeriod +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", totalPremium=" + totalPremium +
                ", status=" + status +
                '}';
    }
}
