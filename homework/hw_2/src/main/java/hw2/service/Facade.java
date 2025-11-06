package hw2.service;

import hw2.command.*;
import hw2.factory.*;
import hw2.model.*;
import hw2.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Facade {
    BankAccountService accountService = new BankAccountService();
    CategoryService categoryService = new CategoryService();
    OperationService operationService = new OperationService(accountService);
    AnalyticsService analyticsService = new AnalyticsService(operationService, accountService, categoryService);
    public Facade(BankAccountService accountService, CategoryService categoryService,
                  OperationService operationService, AnalyticsService analyticsService){
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.operationService = operationService;
        this.analyticsService = analyticsService;
    }
    public BankAccount createAccount(String name, int amount){
        return accountService.createAccount(name, amount);
    }
    public List<BankAccount> getAccounts(){
        return accountService.getAccounts();
    }
    public void deleteAccount(int accountId){
        List<Operation> operations = operationService.getOperationByAccount(accountId);
        for(Operation operation : operations){
            operationService.deleteOperationById(operation.getId());
        }
        accountService.deleteAccount(accountId);
    }
    public Category createCategory(OperationType type, String name){
        return categoryService.createCategory(type, name);
    }
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }
    public void deleteCategory(int categoryId){
        categoryService.deleteCategory(categoryId);
    }
    public Operation addOperation(OperationType type, int accountId, int amount, String desc,
                                  int categoryId){
        return operationService.addOperation(type, accountId, amount, desc, categoryId);
    }
    public List<Operation> getOperations(){
        return operationService.getOperations();
    }
    public List<Operation> getOperationsByAccount(int accountId){
        return operationService.getOperationByAccount(accountId);
    }
    public void deleteOperation(int OperationId){
        operationService.deleteOperationById(OperationId);
    }

    public int getBalDiff(LocalDateTime st, LocalDateTime end){
         return analyticsService.calcDiff(st, end);
    }

    public Map<String, Integer> getCategory(LocalDateTime st, LocalDateTime end){
        return analyticsService.groupCategory(st, end);
    }

    public void recalc(){
        analyticsService.calcBal();
    }

    public int getAllBalance(){
        List<BankAccount> accounts = accountService.getAccounts();
        int res = 0;
        for(BankAccount account : accounts){
            res += account.getBalance();
        }
        return res;
    }

    public void editAccount(int id, String name, int balance){
        accountService.updateAccount(id, name, balance);
    }

    public void editCategory(int id, String name){
        categoryService.updateCategory(id, name);
    }
    public void editOperation(int id, String name){
        operationService.updateOperationDesc(id, name);
    }

}
