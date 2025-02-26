package id.maja.xcore.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IsoListener {
    String mti();
    String processingCode();
}
