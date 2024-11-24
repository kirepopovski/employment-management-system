package net.javaguides.ems.repository;

import net.javaguides.ems.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByName(String name); // Check if a department with the given name exists

    Department findByName(String name); // Find a department by its name
}
