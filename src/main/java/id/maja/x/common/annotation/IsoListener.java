package id.maja.x.common.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IsoListener {
    String mti();
    String processingCode();
}
