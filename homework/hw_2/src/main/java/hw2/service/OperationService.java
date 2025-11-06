package hw2.service;

import hw2.factory.Factory;
import hw2.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import hw2.factory.Factory;
import hw2.model.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class OperationService {
    private List<Operation> operations;
    private BankAccountService accountService;

    public OperationService(BankAccountService accountService){
        this.operations = new ArrayList<>();
        this.accountService = accountService;
    }

    public Operation addOperation(OperationType type, int accountId, int amount, String desc, int categoryId){
        Operation op = Factory.createOperation(type, accountId, amount, desc, categoryId);
        operations.add(op);
        accountService.updateBalance(accountId, amount, type);
        return op;
    }

    public void addOperation(Operation op){
        operations.add(op);
        accountService.updateBalance(op.getBankAccountId(), op.getAmount(), op.getType());
    }

    public  List<Operation> getOperations(){
        return  new ArrayList<Operation>(operations);
    }

    public List<Operation> getOperationByAccount(int accountId){
        List <Operation> res = new ArrayList<Operation>();
        for(Operation operation : operations){
            if(operation.getBankAccountId() == accountId){
                res.add(operation);
            }
        }
        return res;
    }

    public List<Operation> getOperationByCategory(int categoryId){
        List <Operation> res = new ArrayList<Operation>();
        for(Operation operation : operations){
            if(operation.getCategoryId() == categoryId){
                res.add(operation);
            }
        }
        return res;
    }

    public List<Operation> getOperationByTime(LocalDateTime st, LocalDateTime end){
        List <Operation> res = new ArrayList<Operation>();
        for(Operation operation : operations){
            LocalDateTime now = operation.getDate();
            if(!now.isBefore(st) && !now.isAfter(end)){
                res.add(operation);
            }
        }
        return res;
    }

    public List<Operation> getOperationByType(OperationType type){
        List <Operation> res = new ArrayList<Operation>();
        for(Operation operation : operations){
            if(operation.getType() == type){
                res.add(operation);
            }
        }
        return res;
    }

    public Operation getOperationById(int id){
        for(Operation operation : operations){
            if(operation.getId() == id){
                return operation;
            }
        }
        throw new IllegalArgumentException("id must be in list");
    }

    public void deleteOperationById(int id){
        Iterator<Operation> it = operations.iterator();
        while(it.hasNext()){
            Operation op = it.next();
            if(op.getId() == id){
                int amount = op.getAmount();
                accountService.updateBalance(op.getBankAccountId(), amount, op.getType());
                it.remove();
            }
        }
    }

    public void updateOperationDesc(int id, String desc) {
        for(Operation operation : operations){
            if(operation.getId() == id){
                operation.setDescription(desc);
                return;
            }
        }
        throw new IllegalArgumentException("id must be in list");
    }

    public int getAmountPeriodType(LocalDateTime st, LocalDateTime end, OperationType type){
        List <Operation> operations1 = getOperationByTime(st, end);
        int amount = 0;
        for(Operation operation : operations1){
            if(operation.getType() == type){
                amount += operation.getAmount();
            }
        }
        return amount;
    }
}
