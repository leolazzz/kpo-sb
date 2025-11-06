package hw2.strategy;

import hw2.model.Operation;

public class AmountValidationStrategy implements ValidationStrategy {

    @Override
    public boolean Validate(Operation operation) {
        if(operation.getAmount() < 0){
            return false;
        }
        return true;
    }

    @Override
    public String ErrorMsg() {
        return "must be positive";
    }
}
