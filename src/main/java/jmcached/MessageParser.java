package jmcached;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MessageParser {

    private static final Logger log = LoggerFactory.getLogger(MessageParser.class);

    public Message parse(byte[] readBytes) {
        final String message = new String(readBytes);
        final String[] parts = message.split("\r\n", 2);

        final String[] header = parts[0].split(" ");

        final String[] split = message.split(" ", 5);
        log.info("splitted = " + Arrays.toString(split));
        final String cmd = split[0];
        final String key = split[1].trim();
        log.info("cmd = " + cmd);
        log.info("key = " + key);

        if(split.length > 2) {
            final int flags = Integer.parseInt(split[2]);
            final String others = split[3];
            log.info("others = " + others);
            final String[] split1 = split[3].split("\r\n");
            final String value = split1[1];
            log.info("value = " + value);

            return new Message(cmd, key, flags, 0, length, value);
        }

        return new Message(cmd, key, 0, 0, 0, null);
    }

}
