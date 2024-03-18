package contract.management.repository.dto;

public class Collateral {
    private int id;
    private String name;
    private float joinAmount;
    private float baseAmount;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getJoinAmount() {
        return joinAmount;
    }

    public float getBaseAmount() {
        return baseAmount;
    }

    @Override
    public String toString() {
        return "Collateral{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", joinAmount=" + joinAmount +
                ", baseAmount=" + baseAmount +
                '}';
    }
}
