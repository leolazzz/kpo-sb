package hw2.model;

import java.time.LocalDateTime;
import hw2.model.OperationType;

public class Operation {
    private int id;
    private OperationType type;
    private int bankAccountId;
    private int amount;
    private LocalDateTime date;
    private String description;
    private int categoryId;
    public Operation(int id, OperationType type, int bankAccountId,
                     int amount, LocalDateTime date, String description, int categoryId) {
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }
    public int getId(){ return id; }
    public OperationType getType(){ return type; }
    public int getBankAccountId(){ return bankAccountId; }
    public int getAmount(){ return amount; }
    public LocalDateTime getDate(){ return date; }
    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
    public int  getCategoryId(){ return categoryId; }
}
