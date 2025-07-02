package lk.ijse.supermarketfx.model;

import lk.ijse.supermarketfx.Dto.CustomerDto;
import lk.ijse.supermarketfx.db.DBConnection;
import lk.ijse.supermarketfx.dao.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    public String getNextId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select customer_id from customer order by customer_id desc limit 1";
        PreparedStatement pst = connection.prepareStatement(sql);

        ResultSet rst = pst.executeQuery();
        char tableChar = 'C';

        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChar + "%03d", nextIdNumber);
            return nextIdString;
        }
        return "C001";
    }

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO customer VALUES (?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, customerDto.getCustomerID());
        pst.setString(2, customerDto.getName());
        pst.setString(3, customerDto.getNic());
        pst.setString(4, customerDto.getEmail());
        pst.setString(5, customerDto.getPhone());

        int i = pst.executeUpdate();
        boolean isSave = i > 0;
        return isSave;

    }
    
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst =  connection.prepareStatement("select * from customer");
        ResultSet rst = pst.executeQuery();
        
        ArrayList<CustomerDto> list = new ArrayList<>();
        while (rst.next()){
            CustomerDto customerDto = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            
            list.add(customerDto);
        }
        return list;
    }

    public boolean deleteCustomer(String customerID) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from customer where customer_id = ?",customerID);
    }

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where customer_id=?",
                customerDto.getName(),
                customerDto.getNic(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                customerDto.getCustomerID()
        );

    }

    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select customer_id from customer");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }


    public String findNameById(String customerID) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT name FROM customer WHERE customer_id = ?",
                customerID
        );
        if (rst.next()) {
            return rst.getString("name");
        }
        return "";
    }

}
