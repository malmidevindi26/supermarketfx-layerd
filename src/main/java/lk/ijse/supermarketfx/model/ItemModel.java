package lk.ijse.supermarketfx.model;

import lk.ijse.supermarketfx.Dto.ItemDto;
import lk.ijse.supermarketfx.Dto.OrderDetailsDto;
import lk.ijse.supermarketfx.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select item_id from item");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    public ItemDto findById(String itemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from item where item_id = ?",
                itemId
        );

        if (rst.next()) {
            return new ItemDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
        }
        return null;
    }

    public boolean reduceQty(OrderDetailsDto orderDetailsDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update item set quantity = quantity-? where item_id=?",
                orderDetailsDto.getQty(),
                orderDetailsDto.getItemId()
        );

    }
}
