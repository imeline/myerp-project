package erp.department.dto;

public record AddDepartmentRequest(
    String name,
    Long parentId
){

}
