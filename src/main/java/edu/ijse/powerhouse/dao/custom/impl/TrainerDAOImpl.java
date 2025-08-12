package edu.ijse.powerhouse.dao.custom.impl;

import edu.ijse.powerhouse.dao.custom.TrainerDAO;
import edu.ijse.powerhouse.entity.Trainer;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrainerDAOImpl implements TrainerDAO {
    @Override
    public ArrayList<Trainer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Trainer");
        ArrayList<Trainer> trainers = new ArrayList<>();
        while (rst.next()) {
            trainers.add(new Trainer(
                    rst.getString("trainer_id"),
                    rst.getString("user_id"),
                    rst.getString("name"),
                    rst.getString("contact"),
                    rst.getString("address"),
                    rst.getInt("age"),
                    rst.getString("specialization"),
                    rst.getString("certification"),
                    rst.getString("hire_date"),
                    rst.getString("bio"),
                    rst.getDouble("rating")));
        }
        return trainers;
    }

    @Override
    public boolean save(Trainer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "INSERT INTO Trainer (trainer_id, user_id, name, contact, address, age, specialization, certification, hire_date, bio, rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getTrainer_id(),
                entity.getUser_id(),
                entity.getName(),
                entity.getContact(),
                entity.getAddress(),
                entity.getAge(),
                entity.getSpecialization(),
                entity.getCertification(),
                entity.getHire_date(),
                entity.getBio(),
                entity.getRating());
    }

    @Override
    public boolean update(Trainer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "UPDATE Trainer SET user_id = ?, name = ?, contact = ?, address = ?, age = ?, specialization = ?, certification = ?, hire_date = ?, bio = ?, rating = ? WHERE trainer_id = ?",
                entity.getUser_id(),
                entity.getName(),
                entity.getContact(),
                entity.getAddress(),
                entity.getAge(),
                entity.getSpecialization(),
                entity.getCertification(),
                entity.getHire_date(),
                entity.getBio(),
                entity.getRating(),
                entity.getTrainer_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT * FROM Trainer WHERE trainer_id = ?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Trainer WHERE trainer_id = ?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT trainer_id FROM Trainer ORDER BY trainer_id DESC LIMIT 1");
        char tableCharacter = 'T';
        if (rst.next()) {
            String lastId = rst.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }
        return tableCharacter + "001";
    }

    @Override
    public boolean isDuplicateTrainer(String contact) throws Exception {
        return SQLUtil.executeQuery("SELECT 1 FROM Trainer WHERE contact = ?", contact).next();
    }

    @Override
    public boolean isDuplicateTrainerForUpdate(String trainerId, String contact) throws Exception {
        return SQLUtil.executeQuery("SELECT 1 FROM Trainer WHERE contact = ? AND trainer_id != ?", contact, trainerId)
                .next();
    }
}
