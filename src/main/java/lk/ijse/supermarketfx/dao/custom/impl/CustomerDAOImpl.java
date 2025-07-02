package lk.ijse.supermarketfx.dao.custom.impl;

import lk.ijse.supermarketfx.dao.SQLUtil;
import lk.ijse.supermarketfx.dao.custom.customerDAO;
import lk.ijse.supermarketfx.db.DBConnection;
import lk.ijse.supermarketfx.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements customerDAO {

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from customer");
        List<Customer> list = new ArrayList<>();
        while (rst.next()) {
            Customer customer = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            list.add(customer);
        }
        return list;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("select customer_id from customer order by customer_id desc limit 1");
        char tableChar = 'C';
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
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO customer VALUES (?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, customer.getId());
        pst.setString(2, customer.getName());
        pst.setString(3, customer.getNic());
        pst.setString(4, customer.getEmail());
        pst.setString(5, customer.getPhone());

        int i = pst.executeUpdate();
        boolean isSave = i > 0;
        return isSave;
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where customer_id=?",
                customer.getName(),
                customer.getNic(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("delete from customer where customer_id = ?",id);
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select customer_id from customer");
        List<String> list = new ArrayList<>();
        while (rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public Optional<Customer> findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT name FROM customer WHERE customer_id = ?",
                id
        );
        if (rst.next()) {
           Optional<String> name = Optional.of(rst.getString(2));
        }
       return Optional.empty();
    }

    @Override
    public List<Customer> searchCustomer(String text) {
        return List.of();
    }
}
