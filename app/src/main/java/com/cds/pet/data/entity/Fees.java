package com.cds.pet.data.entity;

import java.io.Serializable;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/6 15:26
 * @Version: 3.0.0
 * 费用清单
 */
public class Fees implements Serializable {
    /**
     * price : 123.0
     * count : 1
     * name : sdfsadf
     */
    private int id;
    private String price;
    private String count;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
