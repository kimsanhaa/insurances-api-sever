package insurance.management.controller.dto;

import insurance.management.constants.CONTRACT_STATUS;
import lombok.Getter;

@Getter
public class UpdateContract {
    private Boolean isUpdate;
    private int status;

    public UpdateContract(Boolean isUpdate, int status, int contractId) {
        this.isUpdate = isUpdate;
        this.status = status;

    }
}
