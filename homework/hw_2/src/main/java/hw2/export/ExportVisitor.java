package hw2.export;

import hw2.model.*;

import java.util.List;

public interface ExportVisitor {
    String exportAccounts(List<BankAccount> accounts);
    String exportCategories(List<Category> categories);
    String exportOperations(List<Operation> operations);
    String exportAll(List<BankAccount> accounts, List<Category> categories, List<Operation> operations);
}
