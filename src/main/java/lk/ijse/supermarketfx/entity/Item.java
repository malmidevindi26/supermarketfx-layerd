package lk.ijse.supermarketfx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    private String id;
    private String name;
    private int quantity;
   // private double price;
    private BigDecimal price;
}
