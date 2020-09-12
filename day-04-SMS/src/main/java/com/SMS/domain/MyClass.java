package com.SMS.domain;

public class MyClass {
    private Integer num;
    private Integer floor;
    private Integer room;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    //这两个是根据外键查询的
    private String realname;
    private String deptname;

    private Integer cno;

    private String cname;

    private String address;

    private Integer deptno;

    private Integer headmaster;

    public Integer getCno() {
        return cno;
    }

    public void setCno(Integer cno) {
        this.cno = cno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    public Integer getHeadmaster() {
        return headmaster;
    }

    public void setHeadmaster(Integer headmaster) {
        this.headmaster = headmaster;
    }
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "num=" + num +
                ", floor=" + floor +
                ", room=" + room +
                ", realname='" + realname + '\'' +
                ", deptname='" + deptname + '\'' +
                ", cno=" + cno +
                ", cname='" + cname + '\'' +
                ", address='" + address + '\'' +
                ", deptno=" + deptno +
                ", headmaster=" + headmaster +
                '}';
    }
}
