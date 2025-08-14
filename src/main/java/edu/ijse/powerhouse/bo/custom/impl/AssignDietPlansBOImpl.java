package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.AssignDietPlansBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.AssignDietPlansDAO;
import edu.ijse.powerhouse.dto.AssignDietPlansDTO;
import edu.ijse.powerhouse.dto.AssignWorkoutPlansDTO;
import edu.ijse.powerhouse.entity.AssignDietPlans;

import java.sql.SQLException;
import java.util.ArrayList;

public class AssignDietPlansBOImpl implements AssignDietPlansBO {

    AssignDietPlansDAO assignDietPlansDAO =(AssignDietPlansDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ASSIGNDIETPLANS);
    @Override
    public boolean isDietPlanAssigned(String memberId) throws Exception {
        return assignDietPlansDAO.isDietPlanAssigned(memberId);
    }

    @Override
    public boolean isDietPlanAssignedForUpdate(String dietPlanId, String memberId) throws Exception {
        return assignDietPlansDAO.isDietPlanAssignedForUpdate(dietPlanId,memberId);
    }

    @Override
    public ArrayList<AssignDietPlansDTO> getAllAssignedDietPlans() throws SQLException, ClassNotFoundException {
        ArrayList<AssignDietPlans> entity = assignDietPlansDAO.getAll();
        ArrayList<AssignDietPlansDTO> assignDietPlansDTOS = new ArrayList<>();
        for (AssignDietPlans assignDietPlans : entity) {
            assignDietPlansDTOS.add(new AssignDietPlansDTO(
                    assignDietPlans.getMember_id(),
                    assignDietPlans.getDiet_plan_id(),
                    assignDietPlans.getAssigned_date(),
                    assignDietPlans.getEnd_date(),
                    assignDietPlans.getNotes(),
                    assignDietPlans.getAssigned_by()
            ));
        }
        return assignDietPlansDTOS;
    }

    @Override
    public boolean assignDietPlans(AssignDietPlansDTO assignDietPlansDTO) throws SQLException, ClassNotFoundException {
        return assignDietPlansDAO.save(new AssignDietPlans(
                assignDietPlansDTO.getMember_id(),
                assignDietPlansDTO.getDiet_plan_id(),
                assignDietPlansDTO.getAssigned_date(),
                assignDietPlansDTO.getEnd_date(),
                assignDietPlansDTO.getNotes(),
                assignDietPlansDTO.getAssigned_by()
        ));
    }

    @Override
    public boolean updateAssignedDietPlans(AssignDietPlansDTO assignDietPlansDTO) throws SQLException, ClassNotFoundException {
        return assignDietPlansDAO.update(new AssignDietPlans(
                assignDietPlansDTO.getMember_id(),
                assignDietPlansDTO.getDiet_plan_id(),
                assignDietPlansDTO.getAssigned_date(),
                assignDietPlansDTO.getEnd_date(),
                assignDietPlansDTO.getNotes(),
                assignDietPlansDTO.getAssigned_by()
        ));
    }

    @Override
    public boolean existAssignDietPlans(String id) throws SQLException, ClassNotFoundException {
        return assignDietPlansDAO.exist(id);
    }

    @Override
    public boolean deleteAssignDietPlans(String id) throws SQLException, ClassNotFoundException {
        return assignDietPlansDAO.delete(id);
    }

    @Override
    public String generateNewAssignDietPlanId() throws SQLException, ClassNotFoundException {
        return assignDietPlansDAO.generateNewId();
    }
}
