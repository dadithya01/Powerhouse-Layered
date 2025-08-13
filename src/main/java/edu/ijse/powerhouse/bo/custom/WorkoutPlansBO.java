package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.WorkoutPlansDTO;
import edu.ijse.powerhouse.entity.WorkoutPlans;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WorkoutPlansBO extends SuperBO {
    public ArrayList<WorkoutPlansDTO> getAllWorkoutPlans() throws SQLException, ClassNotFoundException;
    public boolean saveWorkoutPlans(WorkoutPlansDTO entity) throws SQLException, ClassNotFoundException;
    public boolean updateWorkoutPlans(WorkoutPlansDTO entity) throws SQLException, ClassNotFoundException;
    public boolean existWorkoutPlans(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteWorkoutPlans(String id) throws SQLException, ClassNotFoundException;
    public String generateNewWorkoutPlanId() throws SQLException, ClassNotFoundException;
}
