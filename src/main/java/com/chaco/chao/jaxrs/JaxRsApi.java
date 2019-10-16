package com.chaco.chao.jaxrs;

import java.util.Observable;

/**
 * author:zhaopeiyan001
 * Date:2019-10-16 20:35
 */
public class JaxRsApi {

    public static void main(String[] args) {
        MyObservable observable = new MyObservable();

    }

    static class MyObservable extends Observable {
        @Override
        public void setChange() {
            super.setChanged();
        }
    }
}
