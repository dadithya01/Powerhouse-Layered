package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.UserBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.UserDAO;
import edu.ijse.powerhouse.dto.UserDTO;
import edu.ijse.powerhouse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<User> entity = userDAO.getAll();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (User user : entity) {
            userDTOS.add(new UserDTO(
                    user.getUserId(),
                    user.getName(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getUserTypeId(),
                    user.getRegistrationDate(),
                    user.getStatus()
            ));
        }
        return userDTOS;
    }

    @Override
    public boolean saveUser(UserDTO userListDto) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(
                userListDto.getUserId(),
                userListDto.getName(),
                userListDto.getPhone(),
                userListDto.getEmail(),
                userListDto.getUserName(),
                userListDto.getPassword(),
                userListDto.getUserTypeId(),
                userListDto.getRegistrationDate(),
                userListDto.getStatus()
        ));
    }

    @Override
    public boolean updateUser(UserDTO userListDto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(
                userListDto.getUserId(),
                userListDto.getName(),
                userListDto.getPhone(),
                userListDto.getEmail(),
                userListDto.getUserName(),
                userListDto.getPassword(),
                userListDto.getUserTypeId(),
                userListDto.getRegistrationDate(),
                userListDto.getStatus()
        ));
    }

    @Override
    public boolean existUser(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.exist(userId);
    }

    @Override
    public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.delete(userId);
    }

    @Override
    public String generateNewUserId() throws SQLException, ClassNotFoundException {
        return userDAO.generateNewId();
    }
}
