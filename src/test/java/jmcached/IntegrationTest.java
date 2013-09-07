package jmcached;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntegrationTest {

    private final int port = 12345;
    private final String host = "0.0.0.0";

    private ServerRunner serverRunner;
    private MemCachedClient memCachedClient;

    @Before
    public void setUp() throws Exception {
        serverRunner = new ServerRunner(host, String.valueOf(port));

        memCachedClient = craeteMemCachedClient();
    }

    private MemCachedClient craeteMemCachedClient() {
        final SockIOPool sockIOPool = SockIOPool.getInstance();
        sockIOPool.setServers(new String[] {host + ":" + port});
        sockIOPool.initialize();
        return new MemCachedClient();
    }

    @Test
    public void setsValueAndThenGetsThisValueBack() throws Exception {
        memCachedClient.add("somekey", "somevalue");

        final String value = (String) memCachedClient.get("somekey");
        assertThat(value, is(equalTo("somevalue")));
    }

    @After
    public void tearDown() throws Exception {
        serverRunner.stop();

    }
}
