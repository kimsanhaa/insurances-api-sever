package insurance.management.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int status;
    private Date startDate;
    private Date endDate;
    private int period;
    private float totalPremium;
    private LocalDateTime createdAt;

}
