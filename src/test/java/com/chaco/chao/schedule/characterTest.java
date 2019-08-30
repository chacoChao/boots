package com.chaco.chao.schedule;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * author:zhaopeiyan001
 * Date:2019-08-29 16:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class characterTest {

    @Test
    public void characterTest() {
        String suffix = Long.toString(System.currentTimeMillis(), Character.MAX_RADIX);
        String suffixx = Long.toString(System.currentTimeMillis());
        String suffixxx = Long.toString(System.currentTimeMillis(), Character.MIN_RADIX);
        Assert.assertNotNull(suffix);
        Assert.assertNotNull(suffixx);
    }

    @Test
    public void stopWatchTest() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        stopWatch.stop();
        long time = stopWatch.getTime();
        Assert.assertNotNull(stopWatch.getTime());
    }
}
