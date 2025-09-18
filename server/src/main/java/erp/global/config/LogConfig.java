package erp.global.config;


import erp.log.config.LogPayloadProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LogPayloadProperties.class)
public class LogConfig {
}
