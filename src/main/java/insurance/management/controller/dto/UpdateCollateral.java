package insurance.management.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateCollateral {
    private Boolean isUpdate;
    private List<Integer> collateralIds;

    public UpdateCollateral(Boolean isUpdate, List<Integer> collateralIds) {
        this.isUpdate = isUpdate;
        this.collateralIds = collateralIds;
    }
}
