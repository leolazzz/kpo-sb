package zoo.Models;

import zoo.interfaces.IInvetory;

public abstract class Thing implements IInvetory{
    protected int inventoryNumber;

    public Thing(int Number){
        setInventoryNumber(Number);
    }

    @Override
    public int getInventoryNumber (){
        return this.inventoryNumber;
    }

    @Override
    public void setInventoryNumber (int inventoryNumber){
        this.inventoryNumber = inventoryNumber;
    }

}
