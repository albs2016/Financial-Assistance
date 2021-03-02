package com.example.financialassistance;

public class income {
    public int income;

    public income(int income) {
        this.income = income;
    }

    public income() {
        this.income = 0;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "" + income;
    }
}
