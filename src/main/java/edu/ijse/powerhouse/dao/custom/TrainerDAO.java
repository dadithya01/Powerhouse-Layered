package edu.ijse.powerhouse.dao.custom;

import edu.ijse.powerhouse.dao.CrudDAO;
import edu.ijse.powerhouse.entity.Trainer;

public interface TrainerDAO extends CrudDAO<Trainer> {
    public boolean isDuplicateTrainer(String contact) throws Exception;

    public boolean isDuplicateTrainerForUpdate(String trainerId, String contact) throws Exception;
}
