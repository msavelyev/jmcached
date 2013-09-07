package jmcached;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Storage {

    private static final Logger log = LoggerFactory.getLogger(Storage.class);

    private Map<String, Message> data = new HashMap<String, Message>();

    public void set(String key, Message value) {
        log.info("set key={}, value={}", key, value);
        data.put(key, value);
    }

    public Message get(String key) {
        log.info("get key={}", key);
        return data.get(key);
    }

}
