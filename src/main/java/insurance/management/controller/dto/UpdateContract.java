package insurance.management.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateContract {
    private int contractId;
    private List<Integer> collaterals;
    private int period;
    private int status;
}
