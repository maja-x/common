package id.maja.xcore.iso8583;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class IsoChannelGroup {
    static DefaultChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void add(Channel channel) {
        log.debug("Adding new channel");
        log.debug("Channel ID = {}", channel.id());
        log.debug("Channel Remote address = {}", channel.remoteAddress());
        log.debug("Channel Local address = {}", channel.localAddress());
        channelGroup.add(channel);
    }

    public static Channel getChannel(ChannelId id) {
        return channelGroup.find(id);
    }

    public static Channel getByChannelByIp(String ip) {
        for (Channel channel : channelGroup) {
            if (channel.remoteAddress().toString().contains(ip)) {
                return channel;
            }
        }
        return null;
    }

    public static Channel getChannelById(String id) {
        for (Channel channel : channelGroup) {
            if (channel.id().toString().equals(id)) {
                return channel;
            }
        }
        return null;
    }

    public static List<Channel> getChannels() {
        return channelGroup.stream().toList();
    }
}
