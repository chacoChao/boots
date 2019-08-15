package com.chaco.chao.design.pattern.observer;

/**
 * 主题（发布者、被观察者）
 * author:zhaopeiyan001
 * Date:2019-08-15 13:58
 */
public interface Subject {
    /**
     * 注册观察者
     * @param observer
     */
    void registerObserver(Observer observer);

    /**
     * 移除观察者
     * @param observer
     */
    void removeObserver(Observer observer);

    /**
     * 通知观察者
     */
    void notifyObservers();
}
