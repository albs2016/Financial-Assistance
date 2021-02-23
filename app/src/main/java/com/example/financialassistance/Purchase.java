package com.example.financialassistance;

public class Purchase {

    private int amount;
    private String description;
    private String account;

    public Purchase() {
        amount = 0;
        description = "";
        account="";
    }

    public Purchase( String account, int amount, String description) {
        this.amount = amount;
        this.description = description;
        this.account = account;
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

    public String pathString()
    {
        return  account + "/" + "Purchase";
    }

    @Override
    public String toString() {
        return description +" : $"+ amount;
    }
}
