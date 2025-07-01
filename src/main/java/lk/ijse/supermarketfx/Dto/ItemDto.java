package lk.ijse.supermarketfx.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDto {
    private String itemId;
    private String name;
    private int qtyOnHand;
    private double unitPrice;
}
