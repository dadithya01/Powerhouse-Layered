package edu.ijse.powerhouse.dao.custom;

import edu.ijse.powerhouse.dao.CrudDAO;
import edu.ijse.powerhouse.entity.AssignDietPlans;

public interface AssignDietPlansDAO extends CrudDAO<AssignDietPlans> {
    public boolean isDietPlanAssigned(String memberId) throws Exception;
    public boolean isDietPlanAssignedForUpdate (String dietPlanId,String memberId) throws Exception;
}
