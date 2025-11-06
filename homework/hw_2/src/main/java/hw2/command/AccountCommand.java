package hw2.command;

import hw2.factory.Factory;
import hw2.model.BankAccount;
import hw2.service.BankAccountService;
import hw2.service.Facade;

public class AccountCommand implements Command {
    private Facade facade;
    private String accountName;
    private int balance;
    private BankAccount account;

    public AccountCommand(Facade facade, String accountName, int balance){
        this.facade = facade;
        this.accountName = accountName;
        this.balance = balance;
    }
    @Override
    public void execute(){
        account = facade.createAccount(accountName, balance);
        System.out.println("Account " + account.getName() + " created");
    }
    @Override
    public String getName(){
        return accountName;
    }
    public BankAccount getAccount(){
        return account;
    }
}
