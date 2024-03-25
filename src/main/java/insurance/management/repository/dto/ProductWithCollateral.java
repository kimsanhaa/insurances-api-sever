package insurance.management.repository.dto;

import java.util.List;


public class ProductWithCollateral {
    private Product product;
    private List<Collateral> collaterals;

    public Product getProduct() {
        return product;
    }

    public List<Collateral> getCollaterals() {
        return collaterals;
    }
    public ProductWithCollateral(Product product, List<Collateral> collaterals) {
        this.product = product;
        this.collaterals = collaterals;
    }

    @Override
    public String toString() {
        return "ProductWithCollateral{" +
                "product=" + product +
                ", collaterals=" + collaterals +
                '}';
    }
}
