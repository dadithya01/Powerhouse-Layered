package edu.ijse.powerhouse.dao.custom.impl;

import edu.ijse.powerhouse.dao.custom.AssignWorkoutPlansDAO;
import edu.ijse.powerhouse.entity.AssignWorkoutPlans;
import edu.ijse.powerhouse.entity.Attendance;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AssignWorkoutPlansDAOImpl implements AssignWorkoutPlansDAO {
    @Override
    public boolean isDuplicateAssignWorkoutPlans(String member_id) throws Exception {
        return SQLUtil.executeQuery("SELECT member_id FROM Member_Workout_Plan WHERE workout_plan_id = ?", member_id).next();
    }

    @Override
    public boolean isDuplicateAssignWorkoutPlansForUpdate(String member_id, String workout_plan_id) throws Exception {
        return SQLUtil.executeQuery("SELECT workout_plan_id FROM Member_Workout_Plan WHERE member_id = ? AND workout_plan_id != ?", member_id, workout_plan_id).next();
    }

    @Override
    public ArrayList<AssignWorkoutPlans> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Member_Workout_Plan");
        ArrayList<AssignWorkoutPlans> assignWorkoutPlans = new ArrayList<>();
        while (rst.next()) {
            assignWorkoutPlans.add(new AssignWorkoutPlans(
                    rst.getString("member_id"),
                    rst.getString("workout_plan_id"),
                    rst.getString("assigned_date"),
                    rst.getString("end_date"),
                    rst.getString("progress"),
                    rst.getString("notes"),
                    rst.getString("assigned_by")
            ));
        }
        return assignWorkoutPlans;
    }

    @Override
    public boolean save(AssignWorkoutPlans entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Member_Workout_Plan (member_id, workout_plan_id, assigned_date, end_date, progress, notes, assigned_by) VALUES (?, ?, ?, ?, ?, ?, ?)",
                entity.getMember_id(),
                entity.getWorkout_plan_id(),
                entity.getAssigned_date(),
                entity.getEnd_date(),
                entity.getProgress(),
                entity.getNotes(),
                entity.getAssigned_by());
    }

    @Override
    public boolean update(AssignWorkoutPlans entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Member_Workout_Plan SET member_id = ?, assigned_date = ?, end_date = ?, progress = ?, notes = ?, assigned_by = ? WHERE workout_plan_id = ?",
                entity.getWorkout_plan_id(),
                entity.getAssigned_date(),
                entity.getEnd_date(),
                entity.getProgress(),
                entity.getNotes(),
                entity.getAssigned_by(),
                entity.getMember_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT workout_plan_id FROM Member_Workout_Plan WHERE workout_plan_id = ?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Member_Workout_Plan WHERE workout_plan_id = ?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery(" SELECT workout_plan_id FROM Member_Workout_Plan ORDER BY workout_plan_id DESC LIMIT 1");
        String tableCharacter = "AW";

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
