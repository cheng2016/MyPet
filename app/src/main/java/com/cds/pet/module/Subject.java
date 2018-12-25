package com.cds.pet.module;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/8 11:58
 * @Version: 3.0.0
 * <p>
 * 抽象被观察者接口
 * 声明了添加、删除、通知观察者方法
 */
public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver(int index, boolean selected, int num);
}
