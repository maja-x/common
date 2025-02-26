package id.maja.xcore.iso8583;

import com.solab.iso8583.IsoMessage;
import io.netty.channel.Channel;

public interface IsoMessageHandler {
    void run(IsoMessage isoMessage, Channel isoChannel);
}
