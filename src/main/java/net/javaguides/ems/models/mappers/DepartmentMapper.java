package net.javaguides.ems.models.mappers;

import net.javaguides.ems.models.Department;
import net.javaguides.ems.models.dto.DepartmentDto;

public class DepartmentMapper {

    public static DepartmentDto mapToDepartmentDto(Department department) {
        return new DepartmentDto(
                department.getId(),
                department.getName(),
                department.getDescription(),
                department.getNumberOfEmployees()
        );
    }

    public static Department mapToDepartment(DepartmentDto departmentDto) {
        return new Department(
                departmentDto.getId(),
                departmentDto.getName(),
                departmentDto.getDescription(),
                null // Employees will be set separately if needed
        );
    }
}
