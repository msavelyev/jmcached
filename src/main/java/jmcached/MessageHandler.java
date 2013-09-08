package jmcached;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MessageHandler {

    private Storage storage = new Storage();

    public void handle(Socket socket, Message message) throws IOException {
        if(message.command.equals("add") || message.command.equals("set")) {
            storage.set(message.key, message);
            writeToSocket(socket, "STORED");
        } else if(message.command.equals("get")) {
            final Message stored = storage.get(message.key);
            if(stored != null) {
                writeToSocket(
                    socket,
                    "VALUE " + message.key + " " + stored.flags + " " + stored.value.length() + "\r\n"
                        + stored.value + "\r\n"
                );
            }
            writeToSocket(socket, "END\r\n");
        }
    }

    private void writeToSocket(Socket socket, String value) throws IOException {
        final OutputStream outputStream = socket.getOutputStream();
        outputStream.write((value + "\r\n").getBytes());
        outputStream.flush();
    }

}
