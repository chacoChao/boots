package com.chaco.chao.faker;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * author:zhaopeiyan001
 * Date:2019-08-07 17:41
 */
@SpringBootTest
@Slf4j
public class fakerTest {

    @Test
    public void fakerT() {
        Faker faker = new Faker();
        String s = faker.address().buildingNumber();
        Assert.assertNotNull(s);
    }
}
