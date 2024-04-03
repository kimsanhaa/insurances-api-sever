package insurance.management.entity;

import jakarta.persistence.*;

@Entity
public class Collateral {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private float joinAmount;
    private float baseAmount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
