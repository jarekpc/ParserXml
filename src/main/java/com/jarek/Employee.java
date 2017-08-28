package com.jarek;

public class Employee {

    private int id;
    private String name;
    private int age;
    private String type;

    public Employee(int id, String name, int age, String type) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Employee Details - ");
        sb.append("Name:" + getName());
        sb.append(", ");
        sb.append("Type:" + getType());
        sb.append(", ");
        sb.append("Id:" + getId());
        sb.append(", ");
        sb.append("Age:" + getAge());
        sb.append(".");

        return sb.toString();
    }
}
