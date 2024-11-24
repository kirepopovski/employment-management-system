package net.javaguides.ems.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.ems.models.enumerations.Role;
import net.javaguides.ems.models.enumerations.TypeSex;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String image;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private TypeSex typeSex;
    private Double salary;
    private Boolean isPresent;
    private Long departmentId;
}
