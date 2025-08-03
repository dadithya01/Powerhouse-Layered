package edu.ijse.powerhouse.dao;

import edu.ijse.powerhouse.dao.custom.impl.UserDAOImpl;
import edu.ijse.powerhouse.dao.custom.impl.UserTypeDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory==null)?new DAOFactory():daoFactory;
    }
    public enum DAOType {
        USER,
        USERTYPE
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case USER:
                return new UserDAOImpl();
            case USERTYPE:
                return new UserTypeDAOImpl();
            default:
                return null;
        }
    }
}
