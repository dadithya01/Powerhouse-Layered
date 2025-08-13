package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.AssignWorkoutPlanBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.AssignWorkoutPlansDAO;
import edu.ijse.powerhouse.dto.AssignWorkoutPlansDTO;
import edu.ijse.powerhouse.dto.AttendanceDTO;
import edu.ijse.powerhouse.entity.AssignWorkoutPlans;
import edu.ijse.powerhouse.entity.Attendance;

import java.sql.SQLException;
import java.util.ArrayList;

public class AssignWorkoutPlanBOImpl implements AssignWorkoutPlanBO {

    AssignWorkoutPlansDAO assignWorkoutPlansDAO = (AssignWorkoutPlansDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ASSIGNWORKOUTPLANS);
    @Override
    public ArrayList<AssignWorkoutPlansDTO> getAllAssignWorkoutPlans() throws SQLException, ClassNotFoundException {
        ArrayList<AssignWorkoutPlans> entity = assignWorkoutPlansDAO.getAll();
        ArrayList<AssignWorkoutPlansDTO> assignWorkoutPlansDTOS = new ArrayList<>();
        for (AssignWorkoutPlans assignWorkoutPlans : entity) {
            assignWorkoutPlansDTOS.add(new AssignWorkoutPlansDTO(
                    assignWorkoutPlans.getMember_id(),
                    assignWorkoutPlans.getWorkout_plan_id(),
                    assignWorkoutPlans.getAssigned_date(),
                    assignWorkoutPlans.getEnd_date(),
                    assignWorkoutPlans.getProgress(),
                    assignWorkoutPlans.getNotes(),
                    assignWorkoutPlans.getAssigned_by()
            ));
        }
        return assignWorkoutPlansDTOS;
    }

    @Override
    public boolean saveAssignWorkoutPlans(AssignWorkoutPlansDTO assignWorkoutPlansDTO) throws SQLException, ClassNotFoundException {
        return assignWorkoutPlansDAO.save(new AssignWorkoutPlans(
                assignWorkoutPlansDTO.getMember_id(),
                assignWorkoutPlansDTO.getWorkout_plan_id(),
                assignWorkoutPlansDTO.getAssigned_date(),
                assignWorkoutPlansDTO.getEnd_date(),
                assignWorkoutPlansDTO.getProgress(),
                assignWorkoutPlansDTO.getNotes(),
                assignWorkoutPlansDTO.getAssigned_by()
        ));
    }

    @Override
    public boolean updateAssignWorkoutPlans(AssignWorkoutPlansDTO assignWorkoutPlansDTO) throws SQLException, ClassNotFoundException {
        return assignWorkoutPlansDAO.update(new AssignWorkoutPlans(
                assignWorkoutPlansDTO.getMember_id(),
                assignWorkoutPlansDTO.getWorkout_plan_id(),
                assignWorkoutPlansDTO.getAssigned_date(),
                assignWorkoutPlansDTO.getEnd_date(),
                assignWorkoutPlansDTO.getProgress(),
                assignWorkoutPlansDTO.getNotes(),
                assignWorkoutPlansDTO.getAssigned_by()
        ));
    }

    @Override
    public boolean deleteAssignWorkoutPlans(String assignWorkoutPlanId) throws SQLException, ClassNotFoundException {
        return assignWorkoutPlansDAO.delete(assignWorkoutPlanId);
    }

    @Override
    public boolean existAssignWorkoutPlans(String assignWorkoutPlanId) throws SQLException, ClassNotFoundException {
        return assignWorkoutPlansDAO.exist(assignWorkoutPlanId);
    }

    @Override
    public String generateNewAssignWorkoutPlansId() throws SQLException, ClassNotFoundException {
        return assignWorkoutPlansDAO.generateNewId();
    }

    @Override
    public boolean isDuplicateAssignWorkoutPlans(String member_id) throws Exception {
        return assignWorkoutPlansDAO.isDuplicateAssignWorkoutPlans(member_id);
    }

    @Override
    public boolean isDuplicateAssignWorkoutPlansForUpdate(String member_id, String workout_plan_id) throws Exception {
        return assignWorkoutPlansDAO.isDuplicateAssignWorkoutPlansForUpdate(member_id,workout_plan_id);
    }
}
