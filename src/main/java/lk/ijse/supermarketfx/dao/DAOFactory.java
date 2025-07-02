package lk.ijse.supermarketfx.dao;

import lk.ijse.supermarketfx.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }
    @SuppressWarnings("unchecked")
    public <T extends SuperDAO> T getDAO(DAOTypes daoType) {
        return switch (daoType) {
            case CUSTOMER -> (T) new CustomerDAOImpl();
            case ITEM -> (T) new ItemDAOImpl();
            case ORDER -> (T) new OrderDAOImpl();
            case ORDER_DETAILS -> (T) new OrderDetailDAOImpl();
            case QUERY -> (T) new QueryDAOImpl();
            default -> null;
        };
    }

}
