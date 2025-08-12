package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.TrainerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TrainerBO extends SuperBO {
    public ArrayList<TrainerDTO> getAllTrainers() throws SQLException, ClassNotFoundException;

    public boolean saveTrainer(TrainerDTO trainerDTO) throws SQLException, ClassNotFoundException;

    public boolean updateTrainer(TrainerDTO trainerDTO) throws SQLException, ClassNotFoundException;

    public boolean existTrainer(String trainerId) throws SQLException, ClassNotFoundException;

    public boolean deleteTrainer(String trainerId) throws SQLException, ClassNotFoundException;

    public String generateNewTrainerId() throws SQLException, ClassNotFoundException;

    public boolean isDuplicateTrainer(String contact) throws Exception;

    public boolean isDuplicateTrainerForUpdate(String trainerId, String contact) throws Exception;
}
