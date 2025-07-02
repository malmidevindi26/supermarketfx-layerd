package lk.ijse.supermarketfx.dao.custom;

import lk.ijse.supermarketfx.dao.CrudDAO;
import lk.ijse.supermarketfx.entity.Customer;

import java.util.List;

public interface customerDAO extends CrudDAO<Customer> {
    List<Customer> searchCustomer(String text);

}
