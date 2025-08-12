package edu.ijse.powerhouse.dao;

import edu.ijse.powerhouse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? new DAOFactory() : daoFactory;
    }

    public enum DAOType {
        USER,
        USERTYPE,
        MEMBER,
        TRAINER,
        EMPLOYEE,
        Attendance,
        ASSIGNWORKOUTPLANS
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case USER:
                return new UserDAOImpl();
            case USERTYPE:
                return new UserTypeDAOImpl();
            case MEMBER:
                return new MemberDAOImpl();
            case TRAINER:
                return new TrainerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case Attendance:
                return new AttendanceDAOImpl();
            case ASSIGNWORKOUTPLANS:
                return new AssignWorkoutPlansDAOImpl();
            default:
                return null;
        }
    }
}
