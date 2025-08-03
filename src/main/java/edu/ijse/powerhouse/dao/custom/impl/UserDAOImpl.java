package edu.ijse.powerhouse.dao.custom.impl;

import edu.ijse.powerhouse.dao.custom.UserDAO;
import edu.ijse.powerhouse.entity.User;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Users");
        ArrayList<User> userList = new ArrayList<>();
        while (rst.next()){
            User user = new User(
                    rst.getString("user_Id"),
                    rst.getString("name"),
                    rst.getString("phone"),
                    rst.getString("email"),
                    rst.getString("Username"),
                    rst.getString("Password"),
                    rst.getString("user_Type_Id"),
                    rst.getString("registration_Date"),
                    rst.getString("Status")
            );
            userList.add(user);
        }
        return userList;
    }

    public boolean save(User entity) throws SQLException, ClassNotFoundException{
        return SQLUtil.executeUpdate("INSERT INTO Users (user_Id, name, phone, email, Username, Password, user_Type_Id, registration_Date, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getUserId(),
                entity.getName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getUserTypeId(),
                entity.getRegistrationDate(),
                entity.getStatus());
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Users SET name = ?, phone = ?, email = ?, Username = ?, Password = ?, user_Type_Id = ?, registration_Date = ?, Status = ? WHERE user_Id = ?",
                entity.getName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getUserTypeId(),
                entity.getRegistrationDate(),
                entity.getStatus(),
                entity.getUserId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT user_Id FROM Users WHERE user_Id = ?", id);
        return rst.next();  
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Users WHERE user_Id = ?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT user_id FROM Users ORDER BY user_id DESC LIMIT 1");
        char tableCharacter = 'U';

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableCharacter+ "1";
    }


    @Override
    public boolean isDuplicateUserForUpdate(String userId, String email, String userName, String phone) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery(
                "SELECT * FROM Users WHERE (email = ? OR Username = ? OR phone = ?) AND user_Id != ?",
                email, userName, phone, userId
        );
        return rs.next();
    }

    @Override
    public boolean isDuplicateUser(String email, String userName, String phone) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery(
                "SELECT * FROM Users WHERE email = ? OR Username = ? OR phone = ?",
                email, userName, phone
        );
        return rs.next();
    }
}