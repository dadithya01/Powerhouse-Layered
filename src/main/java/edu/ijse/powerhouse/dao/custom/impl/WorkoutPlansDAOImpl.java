package edu.ijse.powerhouse.dao.custom.impl;

import edu.ijse.powerhouse.dao.custom.WorkoutPlansDAO;
import edu.ijse.powerhouse.entity.UserType;
import edu.ijse.powerhouse.entity.WorkoutPlans;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkoutPlansDAOImpl implements WorkoutPlansDAO {
    @Override
    public ArrayList<WorkoutPlans> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Workout_Plan");
        ArrayList<WorkoutPlans> workoutPlans = new ArrayList<>();
        while (rst.next()) {
            workoutPlans.add(new WorkoutPlans(
                    rst.getString("workout_plan_id"),
                    rst.getString("name"),
                    rst.getString("description"),
                    rst.getString("difficulty_level"),
                    rst.getString("created_by"),
                    rst.getString("created_date"),
                    rst.getString("duration_weeks")));
        }
        return workoutPlans;
    }

    @Override
    public boolean save(WorkoutPlans entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Workout_Plan (workout_plan_id, name, description, difficulty_level, created_by, created_date, duration_weeks) VALUES (?, ?, ?, ?, ?, ?, ?)",
                entity.getWorkout_plan_id(),
                entity.getName(),
                entity.getDescription(),
                entity.getDifficulty_level(),
                entity.getCreated_by(),
                entity.getCreated_date(),
                entity.getDuration_weeks());
    }

    @Override
    public boolean update(WorkoutPlans entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Workout_Plan SET name = ?, description = ?, difficulty_level = ?, created_by = ?, created_date = ?, duration_weeks = ? WHERE workout_plan_id = ?",
                entity.getName(),
                entity.getDescription(),
                entity.getDifficulty_level(),
                entity.getCreated_by(),
                entity.getCreated_date(),
                entity.getDuration_weeks(),
                entity.getWorkout_plan_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT workout_plan_id FROM Workout_Plan WHERE workout_plan_id = ?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Workout_Plan WHERE workout_plan_id = ?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT workout_plan_id FROM Workout_Plan ORDER BY workout_plan_id DESC LIMIT 1");
        String tableCharacter = "WP";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);

            return nextIdString;
        }
        return tableCharacter + "001";
    }
}
