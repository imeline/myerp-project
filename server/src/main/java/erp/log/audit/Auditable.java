package erp.log.audit;

import erp.log.enums.LogType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {
    LogType type();

    String message() default "";

    String messageEl() default "";
}