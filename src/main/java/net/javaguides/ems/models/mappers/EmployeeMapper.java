package net.javaguides.ems.models.mappers;

import net.javaguides.ems.models.Department;
import net.javaguides.ems.models.dto.EmployeeDto;
import net.javaguides.ems.models.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getImage(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getRole(),
                employee.getTypeSex(),
                employee.getSalary(),
                employee.getIsPresent(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto, Department department) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getImage(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getRole(),
                employeeDto.getTypeSex(),
                employeeDto.getSalary(),
                employeeDto.getIsPresent(),
                department
        );
    }
}
