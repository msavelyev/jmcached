package jmcached;

public class ServerRunner {

    private final Thread thread;
    private JMCached jmCached;

    public ServerRunner(final String host, final String port) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JMCached.main(new String[]{"blah", host, port});
            }
        });
        thread.start();
    }

    public void stop() {
        thread.stop();
    }
}
