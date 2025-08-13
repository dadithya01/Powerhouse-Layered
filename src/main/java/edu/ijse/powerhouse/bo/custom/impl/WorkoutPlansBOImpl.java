package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.WorkoutPlansBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.WorkoutPlansDAO;
import edu.ijse.powerhouse.dto.AttendanceDTO;
import edu.ijse.powerhouse.dto.WorkoutPlansDTO;
import edu.ijse.powerhouse.entity.Attendance;
import edu.ijse.powerhouse.entity.WorkoutPlans;

import java.sql.SQLException;
import java.util.ArrayList;

public class WorkoutPlansBOImpl implements WorkoutPlansBO {

    WorkoutPlansDAO workoutPlansDAO=(WorkoutPlansDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.WORKOUTPLANS);
    @Override
    public ArrayList<WorkoutPlansDTO> getAllWorkoutPlans() throws SQLException, ClassNotFoundException {
        ArrayList<WorkoutPlans> entity = workoutPlansDAO.getAll();
        ArrayList<WorkoutPlansDTO> workoutPlansDTOS = new ArrayList<>();
        for (WorkoutPlans workoutPlans : entity) {
            workoutPlansDTOS.add(new WorkoutPlansDTO(
                    workoutPlans.getWorkout_plan_id(),
                    workoutPlans.getName(),
                    workoutPlans.getDescription(),
                    workoutPlans.getDifficulty_level(),
                    workoutPlans.getCreated_by(),
                    workoutPlans.getCreated_date(),
                    workoutPlans.getDuration_weeks()
            ));
        }
        return workoutPlansDTOS;
    }

    @Override
    public boolean saveWorkoutPlans(WorkoutPlansDTO entity) throws SQLException, ClassNotFoundException {
        return workoutPlansDAO.save(new WorkoutPlans(
                entity.getWorkout_plan_id(),
                entity.getName(),
                entity.getDescription(),
                entity.getDifficulty_level(),
                entity.getCreated_by(),
                entity.getCreated_date(),
                entity.getDuration_weeks()
        ));
    }

    @Override
    public boolean updateWorkoutPlans(WorkoutPlansDTO entity) throws SQLException, ClassNotFoundException {
        return workoutPlansDAO.update(new WorkoutPlans(
                entity.getWorkout_plan_id(),
                entity.getName(),
                entity.getDescription(),
                entity.getDifficulty_level(),
                entity.getCreated_by(),
                entity.getCreated_date(),
                entity.getDuration_weeks()
        ));
    }

    @Override
    public boolean existWorkoutPlans(String id) throws SQLException, ClassNotFoundException {
        return workoutPlansDAO.exist(id);
    }

    @Override
    public boolean deleteWorkoutPlans(String id) throws SQLException, ClassNotFoundException {
        return workoutPlansDAO.delete(id);
    }

    @Override
    public String generateNewWorkoutPlanId() throws SQLException, ClassNotFoundException {
        return workoutPlansDAO.generateNewId();
    }
}
