package insurance.management.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class productContractCollateralId implements Serializable {
    private int productId;
    private int contractId;
    private int collateralId;
}
