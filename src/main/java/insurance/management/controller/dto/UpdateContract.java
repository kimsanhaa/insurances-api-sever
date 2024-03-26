package insurance.management.controller.dto;

import insurance.management.constants.CONTRACT_STATUS;
import lombok.Getter;

@Getter
public class UpdateContract {
    private Boolean isUpdate;
    private CONTRACT_STATUS status;
    private int contractId;
}
