package erp.company.dto.response;

import java.util.List;

public record DepartmentPositionResponse(
        List<String> departments,
        List<String> positions
) {
}
