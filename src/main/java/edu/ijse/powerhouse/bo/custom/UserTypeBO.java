package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.UserTypeDTO;
import edu.ijse.powerhouse.entity.UserType;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserTypeBO extends SuperBO {

    public ArrayList<UserTypeDTO> getAllUserTypes() throws SQLException, ClassNotFoundException;
    public boolean saveUserType(UserTypeDTO userTypeDTO) throws SQLException, ClassNotFoundException;
    public boolean updateUserType(UserTypeDTO userTypeDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteUserType(String id) throws SQLException, ClassNotFoundException;
    public boolean existUserType(String id) throws SQLException, ClassNotFoundException;
    public String generateNewUserTypeId() throws SQLException, ClassNotFoundException;
}
