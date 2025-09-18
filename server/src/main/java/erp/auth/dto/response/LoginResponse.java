package erp.auth.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String token,
        String uuid,
        // service의 login 메소드에서 role은 String으로 사용됨
        String role,
        String name
) {
    public static LoginResponse of(String token, String uuid,
                                   String role, String name) {
        return LoginResponse.builder()
                .token(token)
                .uuid(uuid)
                .role(role)
                .name(name)
                .build();
    }
}
