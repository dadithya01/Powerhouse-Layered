package edu.ijse.powerhouse.dao.custom;

import edu.ijse.powerhouse.dao.CrudDAO;
import edu.ijse.powerhouse.entity.UserType;

import java.sql.SQLException;

public interface UserTypeDAO extends CrudDAO<UserType> {
    public boolean isDuplicateUserTypeForUpdate(String userTypeId, String userTypeName) throws SQLException, ClassNotFoundException;
    public boolean isDuplicateUserType(String userTypeName) throws SQLException, ClassNotFoundException;
}
