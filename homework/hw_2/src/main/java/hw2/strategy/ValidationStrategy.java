package hw2.strategy;

import hw2.model.*;

public interface ValidationStrategy {
    boolean Validate(Operation operation);
    String ErrorMsg();
}
