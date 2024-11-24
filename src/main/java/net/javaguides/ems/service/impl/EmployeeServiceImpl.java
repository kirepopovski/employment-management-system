package net.javaguides.ems.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.ems.models.Department;
import net.javaguides.ems.models.dto.EmployeeDto;
import net.javaguides.ems.models.Employee;
import net.javaguides.ems.models.exceptions.DepartmentNotFoundException;
import net.javaguides.ems.models.exceptions.EmailAlreadyExistsException;
import net.javaguides.ems.models.exceptions.EmployeeNotFoundException;
import net.javaguides.ems.models.exceptions.ResourceNotFoundException;
import net.javaguides.ems.models.mappers.EmployeeMapper;
import net.javaguides.ems.repository.DepartmentRepository;
import net.javaguides.ems.repository.EmployeeRepository;
import net.javaguides.ems.service.EmployeeService;
import net.javaguides.ems.utils.ValidationUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        // Centralized validations
        ValidationUtil.validatePositiveNumber("Salary", employeeDto.getSalary());
        ValidationUtil.validateNonEmptyString("First Name", employeeDto.getFirstName());
        ValidationUtil.validateNonEmptyString("Last Name", employeeDto.getLastName());

        Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto, department);

        try{
            Employee savedEmployee = employeeRepository.save(employee);
            return EmployeeMapper.mapToEmployeeDto(savedEmployee);
        }catch (DataIntegrityViolationException ex){
            // Check if the exception is caused by a unique constraint violation on the email
            if (ex.getMessage().contains("employees.UKqftatm5oorav2hj3mvoyrlh7o")) {
                throw new EmailAlreadyExistsException("The email " + employeeDto.getEmail() + " already exists.");
            }
            throw ex; // Re-throw if it's not related to email uniqueness
        }

    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not exists with given id: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not exists with given id: " + employeeId));

        ValidationUtil.validatePositiveNumber("Salary", updatedEmployee.getSalary());
        ValidationUtil.validateNonEmptyString("First Name", updatedEmployee.getFirstName());
        ValidationUtil.validateNonEmptyString("Last Name", updatedEmployee.getLastName());

        employee.setImage(updatedEmployee.getImage());
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setRole(updatedEmployee.getRole());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setIsPresent(updatedEmployee.getIsPresent());
        // Handle department update if provided
        if (updatedEmployee.getDepartmentId() != null) {
            Department department = departmentRepository.findById(updatedEmployee.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department does not exist with id: " + updatedEmployee.getDepartmentId()));
            employee.setDepartment(department);
        }


        try {
            // Save the updated employee
            Employee updatedEmployeeObj = employeeRepository.save(employee);
            return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
        } catch (DataIntegrityViolationException ex) {
            // Check if the exception is caused by a unique constraint violation on the email
            if (ex.getMessage().contains("employees.UKqftatm5oorav2hj3mvoyrlh7o")) {
                throw new EmailAlreadyExistsException("The email " + updatedEmployee.getEmail() + " already exists.");
            }
            throw ex; // Re-throw if it's not related to email uniqueness
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not exists with given id: " + employeeId)
                );

        employeeRepository.delete(employee);
    }


}
