package com.cds.pet.data.entity;

/**
 * @Author: chengzj
 * @CreateDate: 2018/12/10 10:09
 * @Version: 3.0.0
 */
public class Medicine {
    /**
     * price : 12.8
     * name : 疫苗1
     * id : 1
     */

    private double price;
    private String name;
    private int id;
    boolean selected = false;
    int num = 1;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
