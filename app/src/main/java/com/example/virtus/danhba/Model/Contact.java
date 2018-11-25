package com.example.virtus.danhba.Model;

public class Contact {
    private int id;
    private int isMale;
    private String mName;
    private String mNumber;

    public Contact() {
    }

    public Contact(int isMale, String mName, String mNumber) {
        this.isMale = isMale;
        this.mName = mName;
        this.mNumber = mNumber;
    }

    public Contact(int id, int isMale, String mName, String mNumber) {
        this.id = id;
        this.isMale = isMale;
        this.mName = mName;
        this.mNumber = mNumber;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int isMale() {
        return isMale;
    }

    public void setMale(int male) {
        isMale = male;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
}

