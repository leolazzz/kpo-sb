package hw2.strategy;

import hw2.model.Operation;

import java.util.ArrayList;
import java.util.List;

public class OperationValidation {
    private List<ValidationStrategy> strategyList = new ArrayList<>();

    public void addStrategy(ValidationStrategy strategy){
        strategyList.add(strategy);
    }

    public boolean validate(Operation operation){
        for(ValidationStrategy strategy : strategyList){
            if(!strategy.Validate(operation)){
                System.out.println("Error " + strategy.ErrorMsg());
                return false;
            }
        }
        return true;
    }
}
