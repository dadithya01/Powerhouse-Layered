package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.EmployeeDTO;
import edu.ijse.powerhouse.dto.MemberDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException;

    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException;

    public boolean existEmployee(String employeeId) throws SQLException, ClassNotFoundException;

    public String generateNewEmployeeId() throws SQLException, ClassNotFoundException;

    public boolean isDuplicateEmployee(String contact) throws Exception;

    public boolean isDuplicateEmployeeForUpdate(String employeeId, String contact) throws Exception;
}
