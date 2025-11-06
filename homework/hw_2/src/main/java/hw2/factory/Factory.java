package hw2.factory;

import hw2.model.OperationType;
import hw2.model.Operation;
import hw2.model.Category;
import hw2.model.BankAccount;

import java.time.LocalDateTime;

public class Factory {
    private static int accountIdCounter = 0;
    private static int categoryIdCounter = 0;
    private static int operationIdCounter = 0;

    public static BankAccount createBankAccount(String name, int balance){
        check(balance);
        return new BankAccount(accountIdCounter++, name, balance);
    }

    public static Category createCategory(OperationType type, String name){
        if(name.isEmpty()){
            throw new IllegalArgumentException("name is not empty");
        }
        return new Category(categoryIdCounter++, type, name);
    }

    public static Operation createOperation(OperationType type, int bankAccountId, int balance,
                                            String desc, int categoryId){
        check(balance);
        return new Operation(operationIdCounter++, type, bankAccountId, balance, LocalDateTime.now(), desc, categoryId);
    }

    private static void check(int amount){
        if(amount < 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
