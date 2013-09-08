package jmcached;

import java.util.Date;

/**
 * @author msavelyev
 */
public class DateUtil {

    public static Date nowPlusSeconds(int seconds) {
        return new Date(new Date().getTime() + seconds * 1000);
    }

}
