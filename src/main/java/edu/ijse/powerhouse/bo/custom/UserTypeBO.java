package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.UserTypeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserTypeBO extends SuperBO {

    public ArrayList<UserTypeDTO> getAllUserTypes() throws SQLException, ClassNotFoundException;

    public boolean saveUserType(UserTypeDTO userTypeDTO) throws SQLException, ClassNotFoundException;

    public boolean updateUserType(UserTypeDTO userTypeDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteUserType(String userTypeId) throws SQLException, ClassNotFoundException;

    public boolean existUserType(String userTypeId) throws SQLException, ClassNotFoundException;

    public String generateNewUserTypeId() throws SQLException, ClassNotFoundException;

    public boolean isDuplicateUserTypeForUpdate(String userTypeId, String userTypeName)
            throws SQLException, ClassNotFoundException;

    public boolean isDuplicateUserType(String userTypeName) throws SQLException, ClassNotFoundException;
}
