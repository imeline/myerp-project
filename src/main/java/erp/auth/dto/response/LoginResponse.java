package erp.auth.dto.response;

import erp.auth.enums.ErpAccountRole;
import lombok.Builder;

@Builder
public record LoginResponse(
        String token,
        String uuid,
        ErpAccountRole role
) {
    public static LoginResponse from(String token, String uuid,
                                     ErpAccountRole role) {
        return LoginResponse.builder()
                .token(token)
                .uuid(uuid)
                .role(role)
                .build();
    }
}
