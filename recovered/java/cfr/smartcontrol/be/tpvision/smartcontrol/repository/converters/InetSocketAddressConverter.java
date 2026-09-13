/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.converters;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Converter
@Component
public class InetSocketAddressConverter
implements AttributeConverter<InetSocketAddress, String> {
    private static final Logger logger = LoggerFactory.getLogger(InetSocketAddressConverter.class);

    @Override
    public String convertToDatabaseColumn(InetSocketAddress inetSocketAddress) {
        if (inetSocketAddress == null) {
            return null;
        }
        InetAddress inetAddress = inetSocketAddress.getAddress();
        if (inetAddress == null) {
            return null;
        }
        String ip = inetAddress.getHostAddress();
        if (ip == null) {
            return null;
        }
        int port = inetSocketAddress.getPort();
        if (port <= 0) {
            return null;
        }
        return String.format("%s:%d", ip, port);
    }

    @Override
    public InetSocketAddress convertToEntityAttribute(String inetSocketAddress) {
        if (inetSocketAddress == null || inetSocketAddress.isEmpty()) {
            return null;
        }
        String[] split = inetSocketAddress.split(":");
        if (split.length < 2) {
            return null;
        }
        String ip = split[0];
        String portString = split[1];
        try {
            int port = Integer.parseInt(portString);
            return new InetSocketAddress(ip, port);
        }
        catch (NumberFormatException e) {
            String message = e.getMessage();
            logger.error(message, e);
            return null;
        }
    }
}

