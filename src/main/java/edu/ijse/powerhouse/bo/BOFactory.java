package edu.ijse.powerhouse.bo;

import edu.ijse.powerhouse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return (boFactory == null) ? new BOFactory() : boFactory;
    }

    public enum BOType {
        USER,
        USERTYPE,
        MEMBER,
        TRAINER,
        EMPLOYEE,
        ATTENDANCE,
        ASSIGNWORKOUTPLANS,
        WORKOUTPLANS
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case USER:
                return new UserBOImpl();
            case USERTYPE:
                return new UserTypeBOImpl();
            case MEMBER:
                return new MemberBOImpl();
            case TRAINER:
                return new TrainerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case ASSIGNWORKOUTPLANS:
                return new AssignWorkoutPlanBOImpl();
            case WORKOUTPLANS:
                return new WorkoutPlansBOImpl();

            default:
                return null;
        }
    }
}
