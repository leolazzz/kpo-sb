package hw2.service;

import hw2.factory.Factory;
import hw2.model.BankAccount;
import hw2.model.OperationType;

import java.util.ArrayList;
import java.util.List;

public class BankAccountService {
    private List<BankAccount> accounts;
    private int id;
    public BankAccountService(){
        this.accounts = new ArrayList<>();
        this.id = 1;
    }
    public BankAccount createAccount(String name, int amount){
        BankAccount account = Factory.createBankAccount(name, amount);
        accounts.add(account);
        return account;
    }
    public void addAccount(BankAccount account){
        accounts.add(account);
    }
    public BankAccount getAccountById(int id){
        for(BankAccount account : accounts){
            if(account.getId() == id){
                return account;
            }
        }
        throw new IllegalArgumentException("id must be in list");
    }
    public List<BankAccount> getAccounts(){
        return new ArrayList<>(accounts);
    }

    public void updateAccount(int id, String name, int balance){
        for(BankAccount account : accounts){
            if(account.getId() == id){
                account.setName(name);
                account.setBalance(balance);
            }
        }
        return;
    }

    public void deleteAccount(int id){
        accounts.removeIf(account -> account.getId() == id);
    }

    public void updateBalance(int id, int amount, OperationType type){
        for(BankAccount account : accounts){
            if(account.getId() == id){
                if(OperationType.INCOME == type){
                    account.setBalance(account.getBalance() + amount);
                } else{
                    account.setBalance(account.getBalance() - amount);
                }
            }
        }
        return;
    }

    public void calcBalance(int id, List<Integer> incomes, List<Integer> expenses){
        int income = 0;
        for(int i : incomes){
            income += i;
        }
        int expense = 0;
        for(int i : expenses){
            expense += i;
        }
        for(BankAccount account : accounts){
            if(account.getId() == id){
                account.setBalance(income - expense);
            }
        }
    }
}
