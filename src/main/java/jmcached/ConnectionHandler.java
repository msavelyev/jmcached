package jmcached;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ConnectionHandler {

    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    private MessageParser messageParser = new MessageParser();
    private MessageHandler messageHandler = new MessageHandler();

    public void connected(Socket socket) throws IOException {
        final InputStream inputStream = socket.getInputStream();

        final byte[] buf = new byte[4096];
        while(true) {
            int read = inputStream.read(buf);
            log.info("read " + read);
            final byte[] readBytes = new byte[read];
            System.arraycopy(buf, 0, readBytes, 0, read);
            final Message message = messageParser.parse(readBytes);
            messageHandler.handle(socket, message);

        }
    }

}
