package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.DietPlansDTO;
import edu.ijse.powerhouse.entity.DietPlans;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DietPlansBO extends SuperBO {
    public ArrayList<DietPlansDTO> getAllDietPlans() throws SQLException, ClassNotFoundException;
    public boolean saveDietPlans(DietPlansDTO dietPlansDTO) throws SQLException, ClassNotFoundException;
    public boolean updateDietPlans(DietPlansDTO dietPlansDTO) throws SQLException, ClassNotFoundException;
    public boolean existDietPlans(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteDietPlans(String id) throws SQLException, ClassNotFoundException;
    public String generateNewDietPlanId() throws SQLException, ClassNotFoundException;
    public boolean isDuplicateDietPlan(String name) throws Exception;
    public boolean isDuplicateDietPlanForUpdate(String diet_plan_id, String name) throws Exception;
}
