package jmcached;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class JMCached {

    private static final Logger log = LoggerFactory.getLogger(JMCached.class);
    private final String host;
    private final int port;
    private volatile ServerSocket socket;
    private final ConnectionHandler connectionHandler = new ConnectionHandler();
    private boolean bound;

    public JMCached(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        try {
            log.info("starting server");
            socket = new ServerSocket();
            socket.bind(new InetSocketAddress(host, port));

            synchronized(this) {
                bound = true;
                notifyAll();
            }
            while(!socket.isClosed()) {
                final Socket accept = socket.accept();
                connectionHandler.connected(accept);
            }
            socket.close();
            log.info("done");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        socket.close();
        connectionHandler.stop();
    }

    public synchronized void waitForBind() throws InterruptedException {
        while(!bound) {
            wait();
        }
    }

    public static void main(String[] args) {
        final String host = args[1];
        final String port = args[2];
        new JMCached(host, Integer.parseInt(port));
    }

}
