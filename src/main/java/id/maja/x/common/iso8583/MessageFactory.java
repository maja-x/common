package id.maja.x.common.iso8583;

import com.github.kpavlov.jreactive8583.iso.ISO8583Version;
import com.github.kpavlov.jreactive8583.iso.J8583MessageFactory;
import com.github.kpavlov.jreactive8583.iso.MessageOrigin;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.parse.ConfigParser;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * MessageFactoryHelper
 *
 * Digunakan untuk generate konfigurasi MessageFactory aja si
 * Untuk kebutuhan ISO 8583 Service
 */
public class MessageFactory {
    /**
     * Generate Server Message Factory
     * @return J8583MessageFactory
     * @throws IOException
     */
    public static J8583MessageFactory<IsoMessage> createServerMessageFactory() throws IOException {
        var messageFactory = ConfigParser.createDefault();
        var xmlFile = ResourceUtils.getURL("classpath:j8583.xml");
        //var xmlFile = MessageFactory.class.getClassLoader().getResource("j8583.xml");
        //assert xmlFile != null;
        ConfigParser.configureFromUrl(messageFactory, xmlFile);
        messageFactory.setCharacterEncoding(StandardCharsets.US_ASCII.name());
        messageFactory.setUseBinaryBitmap(false);
        messageFactory.setAssignDate(true);
        return new J8583MessageFactory<>(messageFactory, ISO8583Version.V1987,
                MessageOrigin.ACQUIRER);
    }

    /**
     * Generate Client Message Factory
     * @return J8583MessageFactory
     * @throws IOException
     */
    public static J8583MessageFactory<IsoMessage> createClientMessageFactory() throws IOException {
        var messageFactory = ConfigParser.createDefault();
        var xmlFile = ResourceUtils.getURL("classpath:j8583.xml");
        //var xmlFile = MessageFactory.class.getClassLoader().getResource("j8583.xml");
        //assert xmlFile != null;
        ConfigParser.configureFromUrl(messageFactory, xmlFile);
        messageFactory.setCharacterEncoding(StandardCharsets.US_ASCII.name());
        messageFactory.setUseBinaryBitmap(false);
        messageFactory.setAssignDate(true);
        return new J8583MessageFactory<>(messageFactory, ISO8583Version.V1987,
                MessageOrigin.OTHER);
    }
}