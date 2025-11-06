package hw2.export;

import hw2.model.*;

import java.util.List;

public class JsonExport implements ExportVisitor {
    @Override
    public String exportAccounts(List<BankAccount> accounts) {
        String result = "";
        result += "{\"accounts\":[";
        for(int i = 0; i < accounts.size(); ++i){
            BankAccount tmp = accounts.get(i);
            result += "{\"id\":";
            result += tmp.getId();
            result += ",\"name\":\"";
            result += tmp.getName();
            result += "\",\"balance\":" ;
            result += tmp.getBalance();
            result += "}";
            if(i < accounts.size() - 1){
                result += ",";
            }
        }
        result += "]}";
        return result;
    }

    @Override
    public String exportCategories(List<Category> categories) {
        String result = "";
        result += "{\"categories\":[";
        for(int i = 0; i < categories.size(); ++i){
            Category tmp = categories.get(i);
            result += "{\"id\":";
            result += tmp.getId();
            result += ",\"type\":\"";
            result += tmp.getType();
            result += "\",\"name\":" ;
            result += tmp.getName();
            result += "}";
            if(i < categories.size() - 1){
                result += ",";
            }
        }
        result += "]}";
        return result;
    }

    @Override
    public String exportOperations(List<Operation> operations) {
        String result = "";
        result += "{\"categories\":[";
        for(int i = 0; i < operations.size(); ++i){
            Operation tmp = operations.get(i);
            result += "{\"id\":";
            result += tmp.getId();
            result += ",\"type\":\"";
            result += tmp.getType();
            result += ",\"accountId\":\"";
            result += tmp.getBankAccountId();
            result += ",\"amount\":\"";
            result += tmp.getAmount();
            result += ",\"date\":\"";
            result += tmp.getDate();
            result += ",\"description\":\"";
            result += tmp.getDescription();
            result += ",\"categoryId\":\"";
            result += tmp.getCategoryId();
            result += "}";
            if(i < operations.size() - 1){
                result += ",";
            }
        }
        result += "]}";
        return result;
    }

    @Override
    public String exportAll(List<BankAccount> accounts, List<Category> categories, List<Operation> operations) {
        String res = "";
        res += exportAccounts(accounts);
        res += exportCategories(categories);
        res += exportOperations(operations);
        return res;
    }
}
