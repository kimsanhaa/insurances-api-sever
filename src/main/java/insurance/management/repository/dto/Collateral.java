package insurance.management.repository.dto;

import lombok.Getter;

@Getter
public class Collateral {
    private int id;
    private String name;
    private float joinAmount;
    private float baseAmount;
}
