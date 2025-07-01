package lk.ijse.supermarketfx.model;

import lk.ijse.supermarketfx.Dto.OrderDto;
import lk.ijse.supermarketfx.db.DBConnection;
import lk.ijse.supermarketfx.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrederModel {
    private final OrderDetailModel orderDetailModel = new OrderDetailModel();
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");
        char tableChar = 'O';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1); // "C004"
            String lastIdNUmberString = lastId.substring(1); // "004"
            int lastIdNumber = Integer.parseInt(lastIdNUmberString); // 4
            int nextIdNumber = lastIdNumber + 1; // 5
            String nextIdString = String.format(tableChar + "%03d", nextIdNumber); // "C005"
            return nextIdString;
        }
        return tableChar + "001";
    }

    public boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isSave = CrudUtil.execute(
                    "insert into orders values (?,?,?)",
                    orderDto.getOrderId(),
                    orderDto.getCustomerId(),
                    orderDto.getDate()
            );

            if (isSave) {
                boolean isDetailsSaved = orderDetailModel.saveOrderDetailsList(orderDto.getCartList());
                if (isDetailsSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }
}
