package zoo.Models;

import zoo.interfaces.IAlive;
import zoo.interfaces.IInvetory;

public abstract class Animal implements IAlive, IInvetory {
    protected String name;
    protected int food, inventoryNumber;
    protected boolean isHealth;

    public Animal(String name, int food){
        this.name = name;
        this.food = food;
        this.isHealth = false;
    }

    @Override
    public int getFood (){
        return food;
    }

    @Override
    public void setFood (int food){
        this.food = food;
    }

    @Override
    public String getName (){
        return this.name;
    }

    @Override
    public int getInventoryNumber (){
        return this.inventoryNumber;
    }

    @Override
    public void setInventoryNumber (int inventoryNumber){
        this.inventoryNumber = inventoryNumber;
    }


    public boolean isHealthy () {
        return isHealth;
    }
    public void setHealth (boolean isHealth) {
        this.isHealth = isHealth;
    }
    public abstract boolean isInteractive();
}
