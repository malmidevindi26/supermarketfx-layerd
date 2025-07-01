package lk.ijse.supermarketfx.Dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartTM {
    private String itemId;
    private String name;
    private int cartQty;
    private double unitPrice;
    private double total;
    private Button btmRemove;
}
