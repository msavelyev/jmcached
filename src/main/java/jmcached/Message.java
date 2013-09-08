package jmcached;

public class Message {

    public final String command;
    public final String key;
    public final int flags;
    public final long expiryTime;
    public final long length;
    public final String value;

    public Message(String command, String key, int flags, long expiryTime, long length, String value) {
        this.command = command;
        this.key = key;
        this.flags = flags;
        this.expiryTime = expiryTime;
        this.length = length;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Message{" +
            "command='" + command + '\'' +
            ", key='" + key + '\'' +
            ", flags=" + flags +
            ", expiryTime=" + expiryTime +
            ", length=" + length +
            ", value='" + value + '\'' +
            "} " + super.toString();
    }
}
