package insurance.management.entity;

import jakarta.persistence.*;

@Entity
public class ProductCollateral {

    @EmbeddedId
    private productContractCollateralId id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @MapsId("productId")
    Product product;

    @ManyToOne
    @MapsId("contractId")
    @JoinColumn(name = "contract_id")
    Contract contract;

    @ManyToOne
    @MapsId("collateralId")
    @JoinColumn(name ="collateral_id")
    Collateral collateral;
}
