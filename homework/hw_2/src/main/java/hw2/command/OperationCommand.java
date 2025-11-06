package hw2.command;

import hw2.factory.Factory;
import hw2.model.BankAccount;
import hw2.model.Operation;
import hw2.model.OperationType;
import hw2.service.BankAccountService;
import hw2.service.Facade;
import hw2.service.OperationService;

public class OperationCommand implements Command {
    private Facade facade;
    private OperationType type;
    private int accountId;
    private int amount;
    private String desc;
    private int categoryId;
    private Operation operation;

    public OperationCommand(Facade facade, OperationType type, int accountId,
                            int amount, String desc, int categoryId){
        this.facade = facade;
        this.type = type;
        this.accountId = accountId;
        this.amount = amount;
        this.desc = desc;
        this.categoryId = categoryId;
    }
    @Override
    public void execute(){
        facade.addOperation(type, accountId, amount, desc, categoryId);
        System.out.println("Operation " + type + " " + amount);
    }
    @Override
    public String getName(){
        return type + " " + amount + " " + accountId;
    }
    public Operation getOperation(){
        return operation;
    }
}
