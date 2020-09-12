package com.SMS.domain;

public class Course {
    private Integer kid;

    private String kname;

    private String kpress;

    private Integer tid;

    public Integer getKid() {
        return kid;
    }

    public void setKid(Integer kid) {
        this.kid = kid;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname == null ? null : kname.trim();
    }

    public String getKpress() {
        return kpress;
    }

    public void setKpress(String kpress) {
        this.kpress = kpress == null ? null : kpress.trim();
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }
}