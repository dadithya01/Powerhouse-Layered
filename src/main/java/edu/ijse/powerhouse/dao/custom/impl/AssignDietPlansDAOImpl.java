package edu.ijse.powerhouse.dao.custom.impl;

import edu.ijse.powerhouse.dao.custom.AssignDietPlansDAO;
import edu.ijse.powerhouse.entity.AssignDietPlans;
import edu.ijse.powerhouse.entity.AssignWorkoutPlans;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AssignDietPlansDAOImpl implements AssignDietPlansDAO {
    @Override
    public boolean isDietPlanAssigned(String memberId) throws Exception {
        return SQLUtil.executeQuery("SELECT member_id FROM Member_Diet_Plan WHERE  = ?", memberId).next();
    }

    @Override
    public boolean isDietPlanAssignedForUpdate(String dietPlanId, String memberId) throws Exception {
        return SQLUtil.executeQuery("SELECT diet_plan_id FROM Member_Diet_Plan WHERE member_id = ? AND diet_plan_id != ?", memberId, dietPlanId).next();
    }

    @Override
    public ArrayList<AssignDietPlans> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Member_Diet_Plan");
        ArrayList<AssignDietPlans> assignDietPlans = new ArrayList<>();
        while (rst.next()) {
            assignDietPlans.add(new AssignDietPlans(
                    rst.getString("member_id"),
                    rst.getString("diet_plan_id"),
                    rst.getString("assigned_date"),
                    rst.getString("end_date"),
                    rst.getString("notes"),
                    rst.getString("assigned_by")
            ));
        }
        return assignDietPlans;
    }

    @Override
    public boolean save(AssignDietPlans entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Member_Diet_Plan (member_id, diet_plan_id, assigned_date, end_date, notes, assigned_by) VALUES (?, ?, ?, ?, ?, ?)",
                entity.getMember_id(),
                entity.getDiet_plan_id(),
                entity.getAssigned_date(),
                entity.getEnd_date(),
                entity.getNotes(),
                entity.getAssigned_by());
    }

    @Override
    public boolean update(AssignDietPlans entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Member_Diet_Plan SET member_id = ?, diet_plan_id = ?, assigned_date = ?, end_date = ?, notes = ?, assigned_by = ? WHERE diet_plan_id = ?",
                entity.getMember_id(),
                entity.getDiet_plan_id(),
                entity.getAssigned_date(),
                entity.getEnd_date(),
                entity.getNotes(),
                entity.getAssigned_by(),
                entity.getDiet_plan_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT diet_plan_id FROM Member_Diet_Plan WHERE diet_plan_id = ?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Member_Diet_Plan WHERE diet_plan_id = ?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery(" SELECT diet_plan_id FROM Member_Diet_Plan ORDER BY diet_plan_id DESC LIMIT 1");
        String tableCharacter = "AD";

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
