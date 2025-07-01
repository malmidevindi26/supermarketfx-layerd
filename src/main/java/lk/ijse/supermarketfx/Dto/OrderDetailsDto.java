package lk.ijse.supermarketfx.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDto {
    private String orderId;
    private String itemId;
    private int qty;
    private double price;
}
