package nomouse.biz.common.validation;

/**
 * Created by nomouse on 18/10/9.
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 类ValueIn.java的实现描述：扩展于JSR303
 * <p>
 * 适用于参数固定值的校验
 * <li>枚举类自适应 支持自定义枚举值
 * <li>默认需要检验大小写
 *
 * @author xueliang.cxl 2014年3月3日 下午4:24:35
 */
@Constraint(validatedBy = {ValueInValidator.class})
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueIn {

    String message() default "Illegal value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 固定值列表
     */
    String[] values() default {};

    /**
     * 对应的枚举类 当values为null时才会生效
     * <p>
     * 若需要自定义枚举的String 值，在枚举中需要有 getValue 方法
     */
    Class<?> enumClass() default Enum.class;

    /**
     * 枚举类获取String值的方法名 默认为getValue
     */
    String enumStrGetter() default "getValue";

    /**
     * 是否检测大小写 默认为检验大小写
     */
    boolean ignoreCase() default false;

}
