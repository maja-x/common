package id.maja.xcore.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IsoDataElement {
    int length();
    int position();
    boolean padded() default true;
    String paddingChar() default " ";
}
