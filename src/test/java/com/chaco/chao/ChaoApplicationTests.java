package com.chaco.chao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChaoApplicationTests {

	@Test
	public void contextLoads() {
		Assert.assertNotNull(new Date());
		System.out.println("====================>>" + new Date());
	}
}
