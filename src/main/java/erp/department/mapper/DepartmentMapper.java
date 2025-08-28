package erp.department.mapper;

import erp.department.domain.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    long nextId();

    int create(@Param("tenantId") Long tenantId,
               @Param("department") Department department);

    int update(@Param("tenantId") Long tenantId,
               @Param("department") Department department);

    Department findById(@Param("tenantId") Long tenantId,
                        @Param("departmentId") Long departmentId);

    boolean existsByName(@Param("tenantId") Long tenantId,
                         @Param("name") String name,
                         @Param("excludeDepartmentId") Long excludeDepartmentId);

    boolean existsById(@Param("tenantId") Long tenantId,
                       @Param("departmentId") Long departmentId);

    List<Department> findRows(@Param("tenantId") Long tenantId,
                              @Param("name") String name,
                              @Param("offset") int offset,
                              @Param("size") int size);

    long countByName(@Param("tenantId") Long tenantId,
                     @Param("name") String name);

    int deleteById(@Param("tenantId") Long tenantId,
                   @Param("departmentId") Long departmentId);

}
