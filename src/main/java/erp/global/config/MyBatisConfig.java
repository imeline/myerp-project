package erp.global.config;

import erp.global.tenant.TenantSqlInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "erp", annotationClass = Mapper.class)
public class MyBatisConfig {

    @Bean
    // @transactional 사용 설정
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Interceptor tenantSqlInterceptor() {
        return new TenantSqlInterceptor();
    }
}
