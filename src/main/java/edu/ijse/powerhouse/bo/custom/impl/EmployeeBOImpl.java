package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.EmployeeBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.EmployeeDAO;
import edu.ijse.powerhouse.dto.EmployeeDTO;
import edu.ijse.powerhouse.dto.MemberDTO;
import edu.ijse.powerhouse.entity.Employee;
import edu.ijse.powerhouse.entity.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO= (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> entity = employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : entity) {
            employeeDTOS.add(new EmployeeDTO(
                    employee.getEmployee_id(),
                    employee.getName(),
                    employee.getContact(),
                    employee.getAddress(),
                    employee.getAge(),
                    employee.getHire_date(),
                    employee.getPosition(),
                    employee.getSalary(),
                    employee.getEmergency_contact()
            ));
        }
        return employeeDTOS;
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
         return employeeDAO.save(new Employee(
                employeeDTO.getEmployee_id(),
                employeeDTO.getName(),
                employeeDTO.getContact(),
                employeeDTO.getAddress(),
                employeeDTO.getAge(),
                employeeDTO.getHire_date(),
                employeeDTO.getPosition(),
                employeeDTO.getSalary(),
                employeeDTO.getEmergency_contact()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(
                employeeDTO.getEmployee_id(),
                employeeDTO.getName(),
                employeeDTO.getContact(),
                employeeDTO.getAddress(),
                employeeDTO.getAge(),
                employeeDTO.getHire_date(),
                employeeDTO.getPosition(),
                employeeDTO.getSalary(),
                employeeDTO.getEmergency_contact()));
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(employeeId);
    }

    @Override
    public boolean existEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.exist(employeeId);
    }

    @Override
    public String generateNewEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewId();
    }

    @Override
    public boolean isDuplicateEmployee(String contact) throws Exception {
        return employeeDAO.isDuplicateEmployee(contact);
    }

    @Override
    public boolean isDuplicateEmployeeForUpdate(String employeeId, String contact) throws Exception {
        return employeeDAO.isDuplicateEmployeeForUpdate(employeeId,contact);
    }
}
