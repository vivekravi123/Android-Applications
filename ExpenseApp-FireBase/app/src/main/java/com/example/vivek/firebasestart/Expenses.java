package com.example.vivek.firebasestart;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class Expenses extends ArrayList<Parcelable> implements Parcelable {

    Expenses(){

    }
    protected Expenses(Parcel in) {
        exp_name = in.readString();
        category = in.readString();
        amount = in.readString();
        date = in.readString();
    }

    public static final Creator<Expenses> CREATOR = new Creator<Expenses>() {
        @Override
        public Expenses createFromParcel(Parcel in) {
            return new Expenses(in);
        }

        @Override
        public Expenses[] newArray(int size) {
            return new Expenses[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(exp_name);
        parcel.writeString(category);
        parcel.writeString(amount);
        parcel.writeString(date);
    }

    String exp_name;
    String category;
    String amount;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Expenses(String exp_name, String category, String amount) {
        this.exp_name = exp_name;
        this.category = category;
        this.amount = amount;
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
        this.date = sdf.format(new Date());
    }

    public Expenses(Map<String, String> map) {
        this.exp_name = map.get("exp_name");
        this.category = map.get("category");
      this.amount = map.get("amount");
       // this.amount = temp;
        this.date = map.get("date");
    }


    public String getExp_name() {
        return exp_name;
    }

    public void setExp_name(String exp_name) {
        this.exp_name = exp_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
