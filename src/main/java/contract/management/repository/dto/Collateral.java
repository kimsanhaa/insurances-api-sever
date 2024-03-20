package contract.management.repository.dto;

public class Collateral {
    private int id;
    private String name;
    private boolean isSign;
    private float joinAmount;
    private float baseAmount;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSign() {
        return isSign;
    }

    public float getJoinAmount() {
        return joinAmount;
    }

    public float getBaseAmount() {
        return baseAmount;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }
}
