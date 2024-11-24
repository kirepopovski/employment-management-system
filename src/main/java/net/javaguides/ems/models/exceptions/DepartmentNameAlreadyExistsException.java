package net.javaguides.ems.models.exceptions;

public class DepartmentNameAlreadyExistsException extends RuntimeException{

    public DepartmentNameAlreadyExistsException(String message) {
        super(message);
    }
}
