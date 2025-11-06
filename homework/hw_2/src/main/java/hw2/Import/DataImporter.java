package hw2.Import;

import hw2.model.*;

import java.util.List;

public abstract class DataImporter {
    public final void ImportData(String path){
        String df = readFile(path);
        List<BankAccount> accountList = getAccounts(df);
        List<Category> categoryList = getCategories(df);
        List<Operation> operationList = getOperations(df);
    }
    protected abstract String readFile(String path);
    protected abstract List<BankAccount> getAccounts(String df);
    protected abstract List<Category> getCategories(String df);
    protected abstract List<Operation> getOperations(String df);
}
