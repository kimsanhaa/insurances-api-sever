package insurance.management.entity;

import jakarta.persistence.*;

@Entity
public class ProductCollateral {
    @Id @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn
    Product product;

    @ManyToOne
    @JoinColumn
    Contract contract;

    @ManyToOne
    @JoinColumn
    Collateral collateral;
}
