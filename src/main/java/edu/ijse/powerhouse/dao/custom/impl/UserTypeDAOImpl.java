package edu.ijse.powerhouse.dao.custom.impl;

import edu.ijse.powerhouse.dao.custom.UserTypeDAO;
import edu.ijse.powerhouse.entity.UserType;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserTypeDAOImpl implements UserTypeDAO {
    @Override
    public ArrayList<UserType> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM User_Types");
        ArrayList<UserType> userTypes = new ArrayList<>();
        while (rst.next()){
            UserType userType= new UserType(
                    rst.getString("user_Type_Id"),
                    rst.getString("type")
            );
            userTypes.add(userType);
        }
        return userTypes;
    }

    @Override
    public boolean save(UserType entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO User_Types (user_type_id, type) VALUES (?, ?, ?, ?, ?)",
                entity.getUser_Type_Id(),
                entity.getUserTypeName());
    }

    @Override
    public boolean update(UserType entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE User_Types SET type = ? WHERE user_type_id = ?",
                entity.getUserTypeName(),
                entity.getUser_Type_Id());
    }

    @Override
    public boolean exist(String userTypeId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT user_type_id FROM User_Types WHERE user_type_id = ?", userTypeId);
        return rst.next();
    }

    @Override
    public boolean delete(String userTypeId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(" Delete From User_Types where user_type_id = ? ", userTypeId);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT user_type_id FROM User_Types ORDER BY user_type_id DESC LIMIT 1");
        String tableCharacter = "UT";

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableCharacter+ "001";
    }

    @Override
    public boolean isDuplicateUserTypeForUpdate(String userTypeId, String userTypeName) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT 1 FROM User_Types WHERE type = ? AND user_type_id != ?", userTypeName, userTypeId);
        return rst.next();
    }

    @Override
    public boolean isDuplicateUserType(String userTypeName) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT 1 FROM User_Types WHERE type = ?", userTypeName);
        return rs.next();
    }
}
