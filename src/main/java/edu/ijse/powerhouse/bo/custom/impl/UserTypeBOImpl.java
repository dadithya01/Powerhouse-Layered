package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.UserTypeBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.UserTypeDAO;
import edu.ijse.powerhouse.dto.UserTypeDTO;
import edu.ijse.powerhouse.entity.UserType;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserTypeBOImpl implements UserTypeBO {

    UserTypeDAO userTypeDAO = (UserTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USERTYPE);
    @Override
    public ArrayList<UserTypeDTO> getAllUserTypes() throws SQLException, ClassNotFoundException {
        ArrayList<UserType> entity = userTypeDAO.getAll();
        ArrayList<UserTypeDTO> userTypeDTOS = new ArrayList<>();
        for (UserType userType : entity){
            userTypeDTOS.add(new UserTypeDTO(
                    userType.getUser_Type_Id(),
                    userType.getUserTypeName()
            ));
        }
        return userTypeDTOS;
    }

    @Override
    public boolean saveUserType(UserTypeDTO userTypeDTO) throws SQLException, ClassNotFoundException {
        return userTypeDAO.save(new UserType(
                userTypeDTO.getUser_Type_Id(),
                userTypeDTO.getUserTypeName()
        ));
    }

    @Override
    public boolean updateUserType(UserTypeDTO userTypeDTO) throws SQLException, ClassNotFoundException {
        return userTypeDAO.update(new UserType(
                userTypeDTO.getUser_Type_Id(),
                userTypeDTO.getUserTypeName()
        ));
    }

    @Override
    public boolean deleteUserType(String id) throws SQLException, ClassNotFoundException {
        return userTypeDAO.delete(id);
    }

    @Override
    public boolean existUserType(String id) throws SQLException, ClassNotFoundException {
        return userTypeDAO.exist(id);
    }

    @Override
    public String generateNewUserTypeId() throws SQLException, ClassNotFoundException {
        return userTypeDAO.generateNewId();
    }
}
