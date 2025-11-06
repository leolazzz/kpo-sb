package hw2.service;

import hw2.command.AccountCommand;
import hw2.factory.Factory;
import hw2.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsService {
    private BankAccountService accountService;
    private OperationService operationService;
    private CategoryService categoryService;
    public AnalyticsService(OperationService operationService, BankAccountService accountService){
        this.operationService = operationService;
        this.accountService = accountService;
        this.categoryService = null;
    }
    public AnalyticsService(OperationService operationService, BankAccountService accountService, CategoryService categoryService){
        this.operationService = operationService;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }
    public int calcDiff(LocalDateTime st, LocalDateTime end){
        int income = operationService.getAmountPeriodType(st, end, OperationType.INCOME);
        int expense = operationService.getAmountPeriodType(st, end, OperationType.EXPENSE);
        return income - expense;
    }
    public Map<String, Integer> groupCategory(LocalDateTime st, LocalDateTime end){
        List <Operation> operations = operationService.getOperationByTime(st, end);
        Map<String, Integer> res = new HashMap<>();
        for(Operation operation : operations){
            int q = operation.getCategoryId();
            String tmp = categoryService.getCategoryNameById(q);
            int tmp2 = operation.getAmount();
            res.put(tmp, tmp2);
        }
        return res;
    }
    public Map<String, Integer> groupCategoryType(LocalDateTime st, LocalDateTime end, OperationType type){
        List <Operation> operations = operationService.getOperationByTime(st, end);
        Map<String, Integer> res = new HashMap<>();
        for(Operation operation : operations){
            if(operation.getType() == type) {
                String tmp = categoryService.getCategoryNameById(operation.getCategoryId());
                int tmp2 = operation.getAmount();
                if(res.containsKey(tmp)){
                    int now = res.get(tmp);
                    res.put(tmp, tmp2 + now);
                } else{
                    res.put(tmp, tmp2);
                }
            }
        }
        return res;
    }
    public int getTotalIncome(LocalDateTime st, LocalDateTime end){
        List <Operation> operations = operationService.getOperationByTime(st, end);
        int res = 0;
        for(Operation operation : operations){
            if(operation.getType() == OperationType.INCOME) {
                res += operation.getAmount();
            }
        }
        return res;
    }
    public int getTotalExpense(LocalDateTime st, LocalDateTime end){
        List <Operation> operations = operationService.getOperationByTime(st, end);
        int res = 0;
        for(Operation operation : operations){
            if(operation.getType() == OperationType.EXPENSE) {
                res += operation.getAmount();
            }
        }
        return res;
    }
    public Map<Integer, Integer> getAccountAmount(){
        List <BankAccount> accounts = accountService.getAccounts();
        Map<Integer, Integer> res = new HashMap<>();
        for(BankAccount account : accounts){
            res.put(account.getId(), account.getBalance());
        }
        return res;
    }
    public void calcBal(){
        List<BankAccount>accounts = accountService.getAccounts();
        List<Operation> operations = operationService.getOperations();
        for(BankAccount account : accounts){
            account.setBalance(0);
        }
        for(Operation operation : operations){
            BankAccount account = accountService.getAccountById(operation.getBankAccountId());
            int cur = account.getBalance();
            if(operation.getType() == OperationType.EXPENSE){
                account.setBalance(cur - operation.getAmount());
            } else{
                account.setBalance(cur + operation.getAmount());
            }
        }
    }
}
