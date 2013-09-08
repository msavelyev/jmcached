package jmcached;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static jmcached.DateUtil.nowPlusSeconds;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class IntegrationTest {

    private int port = 12345;
    private final String host = "0.0.0.0";

    private ServerRunner serverRunner;
    private MemCachedClient memCachedClient;
    private SockIOPool sockIOPool;

    private final String KEY = "somekey";
    private final String DEFAULT_VALUE = "somevalue";

    @Before
    public void setUp() throws Exception {
        serverRunner = new ServerRunner(host, port);
        serverRunner.start();

        memCachedClient = createMemCachedClient();
    }

    private MemCachedClient createMemCachedClient() {
        sockIOPool = SockIOPool.getInstance();
        sockIOPool.setServers(new String[]{host + ":" + port});
        sockIOPool.initialize();
        return new MemCachedClient();
    }

    @Test
    public void getsNullForUnsetKey() throws Exception {
        final String value = (String) memCachedClient.get(KEY);

        assertThat(value, is(nullValue()));
    }

    @Test
    public void setsValueAndThenGetsThisValueBack() throws Exception {
        memCachedClient.add(KEY, DEFAULT_VALUE);

        final String value = (String) memCachedClient.get(KEY);

        assertThat(value, is(equalTo(DEFAULT_VALUE)));
    }

    @Test
    public void updatesValueAndThenGetsUpdatedValue() throws Exception {
        final String NEW_VALUE = "somenewvalue";

        memCachedClient.add(KEY, DEFAULT_VALUE);
        memCachedClient.set(KEY, NEW_VALUE);

        final String value = (String) memCachedClient.get(KEY);

        assertThat(value, is(equalTo(NEW_VALUE)));
    }

    @Test
    public void getsStoredValueIfItHasNotExpiredYet() throws Exception {
        memCachedClient.add(KEY, DEFAULT_VALUE, nowPlusSeconds(1));

        final String unexpiredValue = (String) memCachedClient.get(KEY);

        assertThat(unexpiredValue, is(equalTo(DEFAULT_VALUE)));
    }

    @Test
    public void getsNullIfValueHasExpired() throws Exception {
        memCachedClient.add(KEY, DEFAULT_VALUE, nowPlusSeconds(1));

        Thread.sleep(1010l);
        final String expiredValue = (String) memCachedClient.get(KEY);

        assertThat(expiredValue, is(nullValue()));

    }

    @After
    public void tearDown() throws Exception {
        try {
            sockIOPool.shutDown();
            serverRunner.stop();
        } catch(Throwable e) {
            e.printStackTrace();
        }

        System.out.println("tear down");
    }
}
