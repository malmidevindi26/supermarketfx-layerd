package lk.ijse.supermarketfx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private String id;
    private String name;
    private String nic;
    private String email;
    private String phone;
}
