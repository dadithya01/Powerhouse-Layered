package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;
    public boolean saveUser(UserDTO userListDto) throws SQLException, ClassNotFoundException;
    public boolean updateUser(UserDTO userListDto) throws SQLException, ClassNotFoundException;
    public boolean existUser(String userId) throws SQLException, ClassNotFoundException;
    public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException;
    public String generateNewUserId() throws SQLException, ClassNotFoundException;
}
