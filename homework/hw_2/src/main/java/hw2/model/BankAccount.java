package hw2.model;

public class BankAccount {
    private int id;
    private String name;
    private int balance;
    public BankAccount(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
    public int getId(){ return id; }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    public int getBalance(){ return balance; }
    public void setBalance(int balance){ this.balance = balance; }
}
