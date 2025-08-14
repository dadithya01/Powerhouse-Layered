package edu.ijse.powerhouse.dao.custom;

import edu.ijse.powerhouse.dao.CrudDAO;
import edu.ijse.powerhouse.entity.DietPlans;

public interface DietPlanDAO extends CrudDAO<DietPlans> {
    public boolean isDuplicateDietPlan(String name) throws Exception;
    public boolean isDuplicateDietPlanForUpdate(String diet_plan_id, String name) throws Exception;
}
