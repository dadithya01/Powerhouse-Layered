package edu.ijse.powerhouse.dao.custom;

import edu.ijse.powerhouse.dao.CrudDAO;
import edu.ijse.powerhouse.entity.Employee;

public interface EmployeeDAO extends CrudDAO<Employee> {
    public boolean isDuplicateEmployee(String contact) throws Exception;

    public boolean isDuplicateEmployeeForUpdate(String employeeId, String contact) throws Exception;
}