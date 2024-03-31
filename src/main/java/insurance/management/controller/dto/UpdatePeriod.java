package insurance.management.controller.dto;

import lombok.Getter;

@Getter
public class UpdatePeriod {
    private Boolean isUpdate;
    private int period;

    public UpdatePeriod(Boolean isUpdate, int period) {
        this.isUpdate = isUpdate;
        this.period = period;
    }
}
