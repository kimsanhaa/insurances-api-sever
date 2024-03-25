package insurance.management.repository.dto;

public class Product {
    private int id;
    private String name;
    private int period;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", period=" + period +
                '}';
    }
}
