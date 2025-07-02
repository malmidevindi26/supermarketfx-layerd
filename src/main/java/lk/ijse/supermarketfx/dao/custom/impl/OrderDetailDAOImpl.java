package lk.ijse.supermarketfx.dao.custom.impl;

import lk.ijse.supermarketfx.dao.custom.OrderDetailDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public List<OrderDetailDAO> getAll() {
        return List.of();
    }

    @Override
    public String getNextId() {
        return "";
    }

    @Override
    public boolean save(OrderDetailDAO orderDetailDAO) {
        return false;
    }

    @Override
    public boolean update(OrderDetailDAO orderDetailDAO) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() {
        return null;
    }

    @Override
    public Optional<OrderDetailDAO> findById(String id) {
        return Optional.empty();
    }
}
