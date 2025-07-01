package lk.ijse.supermarketfx.model;

import lk.ijse.supermarketfx.Dto.OrderDetailsDto;
import lk.ijse.supermarketfx.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailModel {
    public final ItemModel itemModel = new ItemModel();
    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> cartList) throws SQLException, ClassNotFoundException {
        for (OrderDetailsDto orderDetailsDto: cartList) {
            boolean isDetailsSaved = saveOrderDetails(orderDetailsDto);
            if (!isDetailsSaved) {
                return false;
            }

            boolean isUpdated = itemModel.reduceQty(orderDetailsDto);
            if (!isUpdated) {
                return false;
            }

            // other tables
        }
        return true;
    }

    private boolean saveOrderDetails(OrderDetailsDto orderDetailsDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into order_details values (?,?,?,?)",
                orderDetailsDto.getOrderId(),
                orderDetailsDto.getItemId(),
                orderDetailsDto.getQty(),
                orderDetailsDto.getPrice()
        );

    }
}
