package lk.ijse.supermarketfx.dao;

import lk.ijse.supermarketfx.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T> extends SuperDAO{
    List<T> getAll() throws SQLException, ClassNotFoundException;

    String getNextId() throws SQLException, ClassNotFoundException;

    boolean save(T t) throws SQLException, ClassNotFoundException;

    boolean update(T t) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    List<String> getAllIds() throws SQLException, ClassNotFoundException;

    Optional<T> findById(String id) throws SQLException, ClassNotFoundException;
}
