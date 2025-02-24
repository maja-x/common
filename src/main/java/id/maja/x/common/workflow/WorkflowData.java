package id.maja.x.common.workflow;

import com.solab.iso8583.IsoMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.Data;

@Data
public class WorkflowData {
    private String id;
    private Object data;
    private ChannelId channelId;
    private String channelIpAddress;

    public WorkflowData(IsoMessage isoMessage, Channel channel) {
        this.setId(isoMessage.getObjectValue(11).toString());
        this.setData(isoMessage);
        this.setChannelId(channel.id());
        this.setChannelIpAddress(channel.localAddress().toString());
    }
}
