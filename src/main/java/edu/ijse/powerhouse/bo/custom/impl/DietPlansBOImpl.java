package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.DietPlansBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.DietPlanDAO;
import edu.ijse.powerhouse.dto.DietPlansDTO;
import edu.ijse.powerhouse.dto.EmployeeDTO;
import edu.ijse.powerhouse.entity.DietPlans;
import edu.ijse.powerhouse.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class DietPlansBOImpl implements DietPlansBO {
    DietPlanDAO dietPlanDAO=(DietPlanDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DIETPLANS);

    @Override
    public ArrayList<DietPlansDTO> getAllDietPlans() throws SQLException, ClassNotFoundException {
        ArrayList<DietPlans> entity = dietPlanDAO.getAll();
        ArrayList<DietPlansDTO> dietPlansDTOS = new ArrayList<>();
        for (DietPlans dietPlans : entity) {
            dietPlansDTOS.add(new DietPlansDTO(
                    dietPlans.getDiet_plan_id(),
                    dietPlans.getName(),
                    dietPlans.getDescription(),
                    dietPlans.getCreated_by(),
                    dietPlans.getCreated_date(),
                    dietPlans.getCalorie_target(),
                    dietPlans.getProtein_target(),
                    dietPlans.getCarbs_target(),
                    dietPlans.getFat_target(),
                    dietPlans.getNotes()
            ));
        }
        return dietPlansDTOS;
    }

    @Override
    public boolean saveDietPlans(DietPlansDTO dietPlansDTO) throws SQLException, ClassNotFoundException {
        return dietPlanDAO.save(new DietPlans(
                dietPlansDTO.getDiet_plan_id(),
                dietPlansDTO.getName(),
                dietPlansDTO.getDescription(),
                dietPlansDTO.getCreated_by(),
                dietPlansDTO.getCreated_date(),
                dietPlansDTO.getCalorie_target(),
                dietPlansDTO.getProtein_target(),
                dietPlansDTO.getCarbs_target(),
                dietPlansDTO.getFat_target(),
                dietPlansDTO.getNotes()
        ));
    }

    @Override
    public boolean updateDietPlans(DietPlansDTO dietPlansDTO) throws SQLException, ClassNotFoundException {
        return dietPlanDAO.update(new DietPlans(
                dietPlansDTO.getName(),
                dietPlansDTO.getDescription(),
                dietPlansDTO.getCreated_by(),
                dietPlansDTO.getCreated_date(),
                dietPlansDTO.getCalorie_target(),
                dietPlansDTO.getProtein_target(),
                dietPlansDTO.getCarbs_target(),
                dietPlansDTO.getFat_target(),
                dietPlansDTO.getNotes(),
                dietPlansDTO.getDiet_plan_id()
        ));
    }

    @Override
    public boolean existDietPlans(String id) throws SQLException, ClassNotFoundException {
        return dietPlanDAO.exist(id);
    }

    @Override
    public boolean deleteDietPlans(String id) throws SQLException, ClassNotFoundException {
        return dietPlanDAO.delete(id);
    }

    @Override
    public String generateNewDietPlanId() throws SQLException, ClassNotFoundException {
        return dietPlanDAO.generateNewId();
    }

    @Override
    public boolean isDuplicateDietPlan(String name) throws Exception {
        return dietPlanDAO.isDuplicateDietPlan(name);
    }

    @Override
    public boolean isDuplicateDietPlanForUpdate(String diet_plan_id, String name) throws Exception {
        return dietPlanDAO.isDuplicateDietPlanForUpdate(diet_plan_id,name);
    }
}
