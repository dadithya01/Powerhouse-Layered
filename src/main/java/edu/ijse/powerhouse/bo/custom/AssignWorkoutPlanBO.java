package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.AssignWorkoutPlansDTO;
import edu.ijse.powerhouse.dto.AttendanceDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AssignWorkoutPlanBO extends SuperBO {
    public ArrayList<AssignWorkoutPlansDTO> getAllAssignWorkoutPlans() throws SQLException, ClassNotFoundException;

    public boolean saveAssignWorkoutPlans(AssignWorkoutPlansDTO assignWorkoutPlansDTO) throws SQLException, ClassNotFoundException;

    public boolean updateAssignWorkoutPlans(AssignWorkoutPlansDTO assignWorkoutPlansDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteAssignWorkoutPlans(String assignWorkoutPlanId) throws SQLException, ClassNotFoundException;

    public boolean existAssignWorkoutPlans(String assignWorkoutPlanId) throws SQLException, ClassNotFoundException;

    public String generateNewAssignWorkoutPlansId() throws SQLException, ClassNotFoundException;

    public boolean isDuplicateAssignWorkoutPlans(String member_id) throws Exception;

    public boolean isDuplicateAssignWorkoutPlansForUpdate(String member_id, String workout_plan_id) throws Exception;
}
