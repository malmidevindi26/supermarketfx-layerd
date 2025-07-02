package lk.ijse.supermarketfx.dao.custom.impl;

import lk.ijse.supermarketfx.Dto.ItemDto;
import lk.ijse.supermarketfx.dao.SQLUtil;
import lk.ijse.supermarketfx.dao.custom.itemDAO;
import lk.ijse.supermarketfx.db.DBConnection;
import lk.ijse.supermarketfx.entity.Customer;
import lk.ijse.supermarketfx.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements itemDAO {
    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from item");
        List<Item> list = new ArrayList<>();
        while (rst.next()) {
            Item item = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getBigDecimal(4)

            );
            list.add(item);
        }
        return list;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select item_id from item order by item_id desc limit 1");
        char tableChar = 'I';
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

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO item VALUES (?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, item.getId());
        pst.setString(2, item.getName());
        pst.setInt(3, item.getQuantity());
        pst.setBigDecimal(4, item.getPrice());

        int i = pst.executeUpdate();
        boolean isSave = i > 0;
        return isSave;
    }

    @Override
    public boolean update(Item item) {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from item where item_id = ?",id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select item_id from item");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<Item> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from item where item_id = ?",
                id
        );

        if (rst.next()) {
            return Optional.of(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getBigDecimal(4)
            ));
        }
        return Optional.empty();
    }
}
