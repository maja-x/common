package id.maja.x.common.trigger;

import com.solab.iso8583.IsoMessage;

public interface IsoTrigger {

    void emit(IsoMessage message) throws RuntimeException;
}
