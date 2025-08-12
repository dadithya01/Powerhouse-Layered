package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.TrainerBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.TrainerDAO;
import edu.ijse.powerhouse.dto.TrainerDTO;
import edu.ijse.powerhouse.entity.Trainer;

import java.sql.SQLException;
import java.util.ArrayList;

public class TrainerBOImpl implements TrainerBO {

    TrainerDAO trainerDAO =(TrainerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TRAINER);
    @Override
    public ArrayList<TrainerDTO> getAllTrainers() throws SQLException, ClassNotFoundException {
        ArrayList<Trainer> entity = trainerDAO.getAll();
        ArrayList<TrainerDTO> trainerDTOS = new ArrayList<>();
        for (Trainer trainer:entity){
            trainerDTOS.add(new TrainerDTO(
                    trainer.getTrainer_id(),
                    trainer.getUser_id(),
                    trainer.getName(),
                    trainer.getContact(),
                    trainer.getAddress(),
                    trainer.getAge(),
                    trainer.getSpecialization(),
                    trainer.getCertification(),
                    trainer.getHire_date(),
                    trainer.getBio(),
                    trainer.getRating()
            ));
        }
        return trainerDTOS;
    }

    @Override
    public boolean saveTrainer(TrainerDTO trainerDTO) throws SQLException, ClassNotFoundException {
        return trainerDAO.save(new Trainer(
                trainerDTO.getTrainer_id(),
                trainerDTO.getUser_id(),
                trainerDTO.getName(),
                trainerDTO.getContact(),
                trainerDTO.getAddress(),
                trainerDTO.getAge(),
                trainerDTO.getSpecialization(),
                trainerDTO.getCertification(),
                trainerDTO.getHire_date(),
                trainerDTO.getBio(),
                trainerDTO.getRating()
        ));
    }

    @Override
    public boolean updateTrainer(TrainerDTO trainerDTO) throws SQLException, ClassNotFoundException {
        return trainerDAO.update(new Trainer(
                trainerDTO.getTrainer_id(),
                trainerDTO.getUser_id(),
                trainerDTO.getName(),
                trainerDTO.getContact(),
                trainerDTO.getAddress(),
                trainerDTO.getAge(),
                trainerDTO.getSpecialization(),
                trainerDTO.getCertification(),
                trainerDTO.getHire_date(),
                trainerDTO.getBio(),
                trainerDTO.getRating()
        ));
    }

    @Override
    public boolean existTrainer(String trainerId) throws SQLException, ClassNotFoundException {
        return trainerDAO.exist(trainerId);
    }

    @Override
    public boolean deleteTrainer(String trainerId) throws SQLException, ClassNotFoundException {
        return trainerDAO.delete(trainerId);
    }

    @Override
    public String generateNewTrainerId() throws SQLException, ClassNotFoundException {
        return trainerDAO.generateNewId();
    }

    @Override
    public boolean isDuplicateTrainer(String contact) throws Exception {
        return trainerDAO.isDuplicateTrainer(contact);
    }

    @Override
    public boolean isDuplicateTrainerForUpdate(String trainerId, String contact) throws Exception {
        return trainerDAO.isDuplicateTrainerForUpdate(trainerId, contact);
    }
}
