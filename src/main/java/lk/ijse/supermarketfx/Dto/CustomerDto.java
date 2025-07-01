package lk.ijse.supermarketfx.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDto {
    private String customerID;
    private String name;
    private String nic;
    private String email;
    private String phone;
}
