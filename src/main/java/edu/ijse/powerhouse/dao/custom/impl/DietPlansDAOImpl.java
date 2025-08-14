package edu.ijse.powerhouse.dao.custom.impl;

import edu.ijse.powerhouse.dao.custom.DietPlanDAO;
import edu.ijse.powerhouse.entity.DietPlans;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DietPlansDAOImpl implements DietPlanDAO {
    @Override
    public boolean isDuplicateDietPlan(String name) throws Exception {
        return SQLUtil.executeQuery("SELECT 1 FROM DietPlans WHERE name = ?", name).next();
    }

    @Override
    public boolean isDuplicateDietPlanForUpdate(String diet_plan_id, String name) throws Exception {
        return SQLUtil.executeQuery("SELECT 1 FROM DietPlans WHERE name = ? AND diet_plan_id != ?", name, diet_plan_id).next();
    }

    @Override
    public ArrayList<DietPlans> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Diet_Plan");
        ArrayList<DietPlans> dietPlans = new ArrayList<>();
        while (rst.next()) {
            dietPlans.add(new DietPlans(
                    rst.getString("diet_plan_id"),
                    rst.getString("name"),
                    rst.getString("description"),
                    rst.getString("created_by"),
                    rst.getString("created_date"),
                    rst.getString("calorie_target"),
                    rst.getString("protein_target"),
                    rst.getString("carbs_target"),
                    rst.getString("fat_target"),
                    rst.getString("notes")
            ));
        }
        return dietPlans;
    }

    @Override
    public boolean save(DietPlans dietPlans) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Diet_Plan (diet_plan_id, name, description, created_by, created_date, calorie_target, protein_target, carbs_target, fat_target, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                dietPlans.getDiet_plan_id(),
                dietPlans.getName(),
                dietPlans.getDescription(),
                dietPlans.getCreated_by(),
                dietPlans.getCreated_date(),
                dietPlans.getCalorie_target(),
                dietPlans.getProtein_target(),
                dietPlans.getCarbs_target(),
                dietPlans.getFat_target(),
                dietPlans.getNotes());
    }

    @Override
    public boolean update(DietPlans dietPlans) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Diet_Plan SET name = ?, description = ?, created_by = ?, created_date = ?, calorie_target = ?, protein_target = ?, carbs_target = ?, fat_target = ?, notes = ? WHERE diet_plan_id = ?",
                dietPlans.getName(),
                dietPlans.getDescription(),
                dietPlans.getCreated_by(),
                dietPlans.getCreated_date(),
                dietPlans.getCalorie_target(),
                dietPlans.getProtein_target(),
                dietPlans.getCarbs_target(),
                dietPlans.getFat_target(),
                dietPlans.getNotes(),
                dietPlans.getDiet_plan_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT diet_plan_id FROM Diet_Plan WHERE diet_plan_id = ?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Diet_Plan WHERE diet_plan_id = ?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery(" SELECT employee_id FROM Employee ORDER BY employee_id DESC LIMIT 1");
        String tableCharacter = "DP";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);

            return nextIdString;
        }
        return tableCharacter + "001";
    }
}
