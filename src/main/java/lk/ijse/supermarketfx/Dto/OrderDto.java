package lk.ijse.supermarketfx.Dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private String orderId;
    private String customerId;
    private Date date;
    private ArrayList<OrderDetailsDto> cartList;

//    public OrderDto(String orderId, String selectedCustomerId, Date date, ArrayList<OrderDetailsDto> cartList) {
//        this.orderId = orderId;
//        this.customerId = selectedCustomerId;
//        this.date = date;
//        this.cartList = cartList;
//    }
}
