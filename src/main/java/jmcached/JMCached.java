package jmcached;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class JMCached {

    private static final Logger log = LoggerFactory.getLogger(JMCached.class);

    public static void main(String[] args) {
        try {
            final String host = args[1];
            final String port = args[2];

            log.info("started server");

            final ServerSocket socket = new ServerSocket();
            socket.bind(new InetSocketAddress(host, Integer.parseInt(port)));

            final ConnectionHandler connectionHandler = new ConnectionHandler();

            while(true) {
                final Socket accept = socket.accept();
                connectionHandler.connected(accept);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
