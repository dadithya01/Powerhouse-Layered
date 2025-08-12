package edu.ijse.powerhouse.dao.custom.impl;

import edu.ijse.powerhouse.dao.custom.EmployeeDAO;
import edu.ijse.powerhouse.entity.Employee;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean isDuplicateEmployee(String contact) throws Exception {
        return SQLUtil.executeQuery("SELECT 1 FROM Employee WHERE contact = ?", contact).next();
    }

    @Override
    public boolean isDuplicateEmployeeForUpdate(String employeeId, String contact) throws Exception {
        return SQLUtil.executeQuery("SELECT 1 FROM Employee WHERE contact = ? AND employee_id != ?", contact, employeeId).next();
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Employee");
        ArrayList<Employee> employees = new ArrayList<>();
        while (rst.next()) {
            employees.add(new Employee(
                    rst.getString("employee_id"),
                    rst.getString("name"),
                    rst.getString("contact"),
                    rst.getString("address"),
                    rst.getInt("age"),
                    rst.getString("hire_date"),
                    rst.getString("position"),
                    rst.getDouble("salary"),
                    rst.getString("emergency_contact")
            ));
        }
        return employees;
    }

    @Override
    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Employee (employee_id, name, contact, address, age, hire_date, position, salary, emergency_contact) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getEmployee_id(),
                entity.getName(),
                entity.getContact(),
                entity.getAddress(),
                entity.getAge(),
                entity.getHire_date(),
                entity.getPosition(),
                entity.getSalary(),
                entity.getEmergency_contact());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Employee SET name = ?, contact = ?, address = ?, age = ?, hire_date = ?, position = ?, salary = ?, emergency_contact = ? WHERE employee_id = ?",
                entity.getName(),
                entity.getContact(),
                entity.getAddress(),
                entity.getAge(),
                entity.getHire_date(),
                entity.getPosition(),
                entity.getSalary(),
                entity.getEmergency_contact(),
                entity.getEmployee_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT employee_id FROM Employee WHERE employee_id = ?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Employee WHERE employee_id = ?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery(" SELECT employee_id FROM Employee ORDER BY employee_id DESC LIMIT 1");
        char tableCharacter = 'E';

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);

            return nextIdString;
        }
        return tableCharacter + "001";
    }
}
