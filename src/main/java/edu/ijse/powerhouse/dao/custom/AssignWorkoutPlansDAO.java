package edu.ijse.powerhouse.dao.custom;

import edu.ijse.powerhouse.dao.CrudDAO;
import edu.ijse.powerhouse.entity.AssignWorkoutPlans;

public interface AssignWorkoutPlansDAO extends CrudDAO<AssignWorkoutPlans> {
    public boolean isDuplicateAssignWorkoutPlans(String member_id) throws Exception;

    public boolean isDuplicateAssignWorkoutPlansForUpdate(String member_id, String workout_plan_id) throws Exception;
}
