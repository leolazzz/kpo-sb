package hw2.strategy;

import hw2.model.Operation;

public class CategoryValidationStrategy implements ValidationStrategy {
    @Override
    public boolean Validate(Operation operation) {
        return operation.getCategoryId() >= 0;
    }

    @Override
    public String ErrorMsg() {
        return "CategoryId() must be positive";
    }
}
