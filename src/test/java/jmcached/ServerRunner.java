package jmcached;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerRunner {

    private static final Logger log = LoggerFactory.getLogger(ServerRunner.class);

    private final Thread thread;
    private final JMCached jmCached;

    public ServerRunner(final String host, final int port) {
        jmCached = new JMCached(host, port);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                jmCached.run();
            }
        });
    }

    public void start() throws InterruptedException {
        thread.start();
        jmCached.waitForBind();
    }

    public void stop() throws IOException, InterruptedException {
        jmCached.stop();
        thread.join();
        log.info("stopped");
    }
}
