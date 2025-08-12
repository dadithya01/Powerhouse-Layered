package edu.ijse.powerhouse.bo;

import edu.ijse.powerhouse.bo.custom.impl.MemberBOImpl;
import edu.ijse.powerhouse.bo.custom.impl.TrainerBOImpl;
import edu.ijse.powerhouse.bo.custom.impl.UserBOImpl;
import edu.ijse.powerhouse.bo.custom.impl.UserTypeBOImpl;

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
        TRAINER
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
            default:
                return null;
        }
    }
}
