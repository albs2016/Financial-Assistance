package com.example.financialassistance;

import java.util.Calendar;
import java.util.TimeZone;

public class Purchase {

    private int amount;
    private String description;
    private String account;
    private int day;
    private int month;
    private int year;
    private int minute;
    private int second;
    private int hour;

    public Purchase() {
        amount = 0;
        description = "";
        account="";
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        day = localCalendar.get(Calendar.DATE);
        month = localCalendar.get(Calendar.MONTH) + 1;
        year = localCalendar.get(Calendar.YEAR);
        minute = localCalendar.get(Calendar.MINUTE);
        second = localCalendar.get(Calendar.SECOND);
        hour =  localCalendar.get(Calendar.HOUR);
    }

    public Purchase( String account, int amount, String description,int second, int minute, int hour, int day, int month, int year) {
        this.amount = amount;
        this.description = description;
        this.account = account;
        this.minute = minute;
        this.day = day;
        this.year= year;
        this.month= month;
        this.second = second;
        this.hour= hour;
    }

    public String getAccount() { return account; }

    public void setAccount(String account) { this.account = account; }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMinute() { return minute; }

    public void setMinute(int minute) { this.minute = minute;}

    public int getSecond() { return second; }

    public void setSecond(int second) { this.second = second; }

    public int getHour() { return hour; }

    public void setHour(int hour) { this.hour = hour; }

    public String pathString()
    {
        return  account + "/" +"Budget" +"/" +"Year -" + year + "/" +"Month -"+month + "/" +day + hour+ minute + second   ;
    }

    @Override
    public String toString() {
        return month + "/" + day + ": " + description +" : $"+ amount;
    }
}
