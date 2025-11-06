package hw2.model;

import java.time.LocalDateTime;

public class Category {
    private int id;
    private OperationType type;
    private String name;
    public Category(int id, OperationType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }
    public int getId(){ return id; }
    public OperationType getType(){ return type; }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
}
