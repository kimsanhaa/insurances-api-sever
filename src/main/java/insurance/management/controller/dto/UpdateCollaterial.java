package insurance.management.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateCollaterial {
    private Boolean isUpdate;
    private List<Integer> collateralIds;
}
