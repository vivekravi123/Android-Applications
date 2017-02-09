package com.example.vivek.inclass08;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {

    String ExpName;
    String amount;
    String Category;
    String date;

    public Expense(String expName, String amount, String category) {
        this.ExpName = expName;
        this.amount = amount;
        this.Category = category;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        this.date = sdf.format(new Date());

    }

    @Override
    public String toString() {
        return "Expense{" +
                "ExpName='" + ExpName + '\'' +
                ", amount='" + amount + '\'' +
                ", Category='" + Category + '\'' +
                '}';
    }

    public String getExpName() {
        return ExpName;
    }

    public void setExpName(String expName) {
        ExpName = expName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
