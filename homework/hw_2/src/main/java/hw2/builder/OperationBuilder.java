package hw2.builder;

import hw2.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OperationBuilder {
    private int id;
    private OperationType type;
    private int bankAccountId;
    private int amount;
    private LocalDateTime date;
    private String description;
    private int categoryId;

    public OperationBuilder setId(int id){
        this.id = id;
        return this;
    }

    public OperationBuilder setType(OperationType type){
        this.type = type;
        return this;
    }

    public OperationBuilder setBankAccountId(int bankAccountId){
        this.bankAccountId = bankAccountId;
        return this;
    }

    public OperationBuilder setAmount(int amount){
        this.amount = amount;
        return this;
    }

    public OperationBuilder setDate(LocalDateTime date){
        this.date = date;
        return this;
    }

    public OperationBuilder setDescription(String description){
        this.description = description;
        return this;
    }

    public OperationBuilder setCategoryId(int categoryId){
        this.categoryId = categoryId;
        return this;
    }
    public Operation build(){
        return  new Operation(id, type, bankAccountId, amount, date, description, categoryId);
    }
}
