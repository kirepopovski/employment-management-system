package net.javaguides.ems.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.ems.models.Department;
import net.javaguides.ems.models.dto.DepartmentDto;
import net.javaguides.ems.models.exceptions.DepartmentNameAlreadyExistsException;
import net.javaguides.ems.models.exceptions.DepartmentNotFoundException;
import net.javaguides.ems.models.mappers.DepartmentMapper;
import net.javaguides.ems.repository.DepartmentRepository;
import net.javaguides.ems.repository.EmployeeRepository;
import net.javaguides.ems.service.DepartmentService;
import net.javaguides.ems.utils.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        // Centralized validations
        ValidationUtil.validateNonEmptyString("Department Name", departmentDto.getName());

        // Check if the department name already exists
        if (departmentRepository.existsByName(departmentDto.getName())) {
            throw new DepartmentNameAlreadyExistsException("The department name " + departmentDto.getName() + " already exists.");
        }

        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department is not exists with given id: " + departmentId));

        // Count the number of employees in the department
        long employeeCount = employeeRepository.countEmployeesByDepartmentId(departmentId);

        DepartmentDto departmentDto = DepartmentMapper.mapToDepartmentDto(department);
        departmentDto.setNumberOfEmployees((int) employeeCount);
        return departmentDto;
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map((department) -> {
                    DepartmentDto departmentDto = DepartmentMapper.mapToDepartmentDto(department);
                    long employeeCount = employeeRepository.countEmployeesByDepartmentId(department.getId());
                    departmentDto.setNumberOfEmployees((int) employeeCount); // set the dynamic employee count
                    return departmentDto;
                }
            ).collect(Collectors.toList());
    }


    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment) {

        // Centralized validations
        ValidationUtil.validateNonEmptyString("Department Name", updatedDepartment.getName());

        // Check if the department name already exists (and not for the same department)
        Department existingDepartment = departmentRepository.findByName(updatedDepartment.getName());
        if (existingDepartment != null && !existingDepartment.getId().equals(departmentId)) {
            throw new DepartmentNameAlreadyExistsException("The department name " + updatedDepartment.getName() + " already exists.");
        }

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department is not exists with given id: " + departmentId));

        department.setName(updatedDepartment.getName());
        department.setDescription(updatedDepartment.getDescription());

        Department updatedDepartmentObj = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(updatedDepartmentObj);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department is not exists with given id: " + departmentId));
        departmentRepository.delete(department);
    }
}
