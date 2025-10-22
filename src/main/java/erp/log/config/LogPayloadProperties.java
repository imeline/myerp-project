package erp.log.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "log.payload")
public class LogPayloadProperties {

    @Positive
    private int maxPayloadBytes = 8 * 1024;

    @Positive
    private int maxStringLen = 512;

    @Positive
    private int maxArrayLen = 50;

    @NotEmpty
    private List<String> sensitiveKeys = Arrays.asList(
            "password", "pwd", "pass", "secret", "token", "access_token",
            "authorization", "api_key", "apikey", "card", "ssn"
    );

    @NotEmpty
    private List<String> heavyFields = Arrays.asList(
            "stack", "stacktrace", "trace", "requestBody", "responseBody", "body",
            "headers", "raw", "content", "query", "params", "form", "multipart", "sql"
    );
}