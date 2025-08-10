package edu.ijse.powerhouse.dao.custom;

import edu.ijse.powerhouse.dao.CrudDAO;
import edu.ijse.powerhouse.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public boolean isDuplicateUserForUpdate(String userId, String email, String userName, String phone)
            throws SQLException, ClassNotFoundException;

    public boolean isDuplicateUser(String email, String userName, String phone)
            throws SQLException, ClassNotFoundException;
}
