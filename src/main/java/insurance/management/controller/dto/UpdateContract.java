package insurance.management.controller.dto;

import insurance.management.constants.CONTRACT_STATUS;
import lombok.Getter;

@Getter
public class UpdateContract {
    private Boolean isUpdate;
    private int status;
    private int contractId;
}
