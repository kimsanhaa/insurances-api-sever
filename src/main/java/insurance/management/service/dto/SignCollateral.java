package insurance.management.service.dto;

import insurance.management.repository.dto.Collateral;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignCollateral {
    private int id;
    private String name;
    private float joinAmount;
    private float baseAmount;

    public static SignCollateral of (Collateral collateral){
        return SignCollateral.builder()
                .id(collateral.getId())
                .name(collateral.getName())
                .joinAmount(collateral.getJoinAmount())
                .baseAmount(collateral.getBaseAmount())
                .build();
    }
}
