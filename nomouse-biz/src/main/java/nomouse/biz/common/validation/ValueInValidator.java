package nomouse.biz.common.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * Created by nomouse on 18/10/9.
 */
public class ValueInValidator implements ConstraintValidator<ValueIn, String> {

    private String[] values = null;
    private String enumStrGetter = "getValue";
    private boolean ignoreCase = false;

    public void initialize(ValueIn valueIn) {
        this.ignoreCase = valueIn.ignoreCase();
        this.values = valueIn.values();
        this.enumStrGetter = valueIn.enumStrGetter();
        if (this.values == null || this.values.length == 0) {
            this.values = this.buildValuesByEnumClass(valueIn.enumClass());
        }
    }

    /**
     * @param enumClass
     * @return
     */
    private String[] buildValuesByEnumClass(Class<?> enumClass) {
        if (enumClass != null) {
            if (enumClass.getEnumConstants() != null) {
                String[] values = new String[enumClass.getEnumConstants().length];
                int i = 0;
                for (Object en : enumClass.getEnumConstants()) {
                    values[i] = this.getValueOfEnum((Enum<?>) en, enumClass);
                    i++;
                }
                return values;
            }
        }
        return null;
    }

    /**
     * @param en
     * @return
     */
    private String getValueOfEnum(Enum<?> en, Class<?> enumClass) {
        try {
            // 无参数的getter方法调用，若失败则统一返回en.name()
            Method valueMethod = enumClass.getMethod(enumStrGetter, new Class<?>[]{});
            return valueMethod.invoke(en, new Object[]{}).toString();
        } catch (Exception e) {
            return en.name();
        }
    }

    /**
     * 这里value为空的时候，直接返回true；不做空的判断，如果需要请使用@NotNull
     */
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (values == null || values.length == 0 || value == null) {
            return true;
        } else {
            for (String strValue : values) {
                if (this.ignoreCase) {
                    if (StringUtils.equals(strValue, value)) {
                        return true;
                    }
                } else {
                    if (StringUtils.equalsIgnoreCase(strValue, value)) {
                        return true;
                    }
                }
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.buildErrmsg()).addConstraintViolation();
            return false;
        }
    }

    /**
     * @return
     */
    private String buildErrmsg() {
        StringBuilder strBuilder = new StringBuilder("target value must be one of [");
        for (String strValue : values) {
            strBuilder.append(" ");
            strBuilder.append(strValue);
        }
        strBuilder.append(" ]");
        return strBuilder.toString();
    }
}
