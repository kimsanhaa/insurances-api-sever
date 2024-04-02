package insurance.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private int period;
}
