package com.chaco.chao.proxy;

import com.chaco.chao.handler.MyInvocationHandler;
import com.chaco.chao.service.UserService;
import com.chaco.chao.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Observable;
import java.util.Observer;

/**
 * author:zhaopeiyan001
 * Date:2019-08-13 10:49
 */
@SpringBootTest
@Slf4j
public class ProxyTest {

    @Test
    public void testProxy() {
        UserServiceImpl userService = new UserServiceImpl();

        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(userService);

        UserService proxy = (UserService) myInvocationHandler.getProxy();

        proxy.add();

        Assert.assertNotNull(proxy);

        new Observer() {
            @Override
            public void update(Observable o, Object arg) {

            }
        };
    }
}
