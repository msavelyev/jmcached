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
    private volatile boolean stopped = false;
    private Socket socket;

    public void connected(Socket socket) throws IOException, InterruptedException {
        this.socket = socket;
        final InputStream inputStream = socket.getInputStream();

        final byte[] buf = new byte[4096];
        while(!stopped) {
            if(inputStream.available() > 0) {
                int read = inputStream.read(buf);
                final byte[] readBytes = new byte[read];
                System.arraycopy(buf, 0, readBytes, 0, read);
                final Message message = messageParser.parse(readBytes);
                messageHandler.handle(socket, message);
            } else {
                Thread.sleep(100l);
            }
        }
        log.info("finally stopped");
    }

    public void stop() throws IOException {
        stopped = true;
        socket.close();
    }

}
