package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.AssignDietPlansDTO;
import edu.ijse.powerhouse.dto.AssignWorkoutPlansDTO;
import edu.ijse.powerhouse.entity.AssignDietPlans;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AssignDietPlansBO extends SuperBO {
    public boolean isDietPlanAssigned(String memberId) throws Exception;
    public boolean isDietPlanAssignedForUpdate(String dietPlanId, String memberId) throws Exception;
    public ArrayList<AssignDietPlansDTO> getAllAssignedDietPlans() throws SQLException, ClassNotFoundException;
    public boolean assignDietPlans(AssignDietPlansDTO assignDietPlansDTO) throws SQLException, ClassNotFoundException;
    public boolean updateAssignedDietPlans(AssignDietPlansDTO assignDietPlansDTO) throws SQLException, ClassNotFoundException;
    public boolean existAssignDietPlans(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteAssignDietPlans(String id) throws SQLException, ClassNotFoundException;
    public String generateNewAssignDietPlanId() throws SQLException, ClassNotFoundException;
}
