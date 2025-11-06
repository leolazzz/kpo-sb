package hw2;
import hw2.command.*;
import hw2.factory.*;
import hw2.model.*;
import hw2.service.*;
import hw2.builder.*;
import hw2.strategy.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        BankAccountService accountService = new BankAccountService();
        CategoryService categoryService = new CategoryService();
        OperationService operationService = new OperationService(accountService);
        AnalyticsService analyticsService = new AnalyticsService(operationService, accountService, categoryService);
        Facade facade = new Facade(accountService, categoryService, operationService, analyticsService);
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Choose:");
            System.out.println("1. New account");
            System.out.println("2. New operation");
            System.out.println("3. New category");
            System.out.println("4. All account");
            System.out.println("5. All category");
            System.out.println("6. All operation");
            System.out.println("7. Analytics");
            System.out.println("8. Recalculate balance");
            System.out.println("9. Edit account");
            System.out.println("10. Delete account");
            System.out.println("11. Edit category");
            System.out.println("12. Delete category");
            System.out.println("13. Edit operation");
            System.out.println("14. Delete operation");
            System.out.println("15. OperationBuilder example");
            System.out.println("16. Strategy example");
            System.out.println("0. Exit");
            String cmd = scanner.nextLine();
            switch (cmd){
                case "1":
                    CreateAccount(facade, scanner);
                    break;
                case "2":
                    AddOperation(facade, scanner);
                    break;
                case "3":
                    CreateCategory(facade, scanner);
                    break;
                case "4":
                    allAccounts(facade);
                    break;
                case "5":
                    allCategories(facade);
                    break;
                case "6":
                    allOperations(facade);
                    break;
                case "7":
                    showAnalytics(facade, scanner);
                    break;
                case "8":
                    recalcBalance(facade);
                    break;
                case "9":
                    editAccount(facade, scanner);
                    break;
                case "10":
                    deleteAccount(facade, scanner);
                    break;
                case "11":
                    editCategory(facade, scanner);
                    break;
                case "12":
                    deleteCategory(facade, scanner);
                    break;
                case "13":
                    editOperation(facade, scanner);
                    break;
                case "14":
                    deleteOperation(facade, scanner);
                    break;
                case "15":
                    builderExample(facade);
                    break;
                case "16":
                    strategyExample(facade);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Choose number");
            }
        }
    }

    private static void CreateAccount(Facade facade, Scanner scanner){
        System.out.println("Enter name:");
        String nm = scanner.nextLine();
        System.out.println("Enter balance:");
        int bal = scanner.nextInt();
        Command command = new CommandDecorator(new AccountCommand(facade, nm, bal));
        command.execute();
    }

    private static void CreateCategory(Facade facade, Scanner scanner){
        System.out.println("Enter type:");
        System.out.println("1. Income");
        System.out.println("2. Expense");
        String cmd = scanner.nextLine();
        OperationType type;
        switch (cmd){
            case "1":
                type = OperationType.INCOME;
                break;
            case "2":
                type = OperationType.EXPENSE;
                break;
            default:
                System.out.println("Choose number");
                return;
        }
        System.out.println("Enter name:");
        String nm = scanner.nextLine();
        facade.createCategory(type, nm);
        System.out.println("Category created");
    }

    private static void AddOperation(Facade facade, Scanner scanner){
        System.out.println("Enter type:");
        System.out.println("1. Income");
        System.out.println("2. Expense");
        String cmd = scanner.nextLine();
        OperationType type;
        switch (cmd){
            case "1":
                type = OperationType.INCOME;
                break;
            case "2":
                type = OperationType.EXPENSE;
                break;
            default:
                System.out.println("Choose number");
                return;
        }
        System.out.println("Enter account id:");
        int id = scanner.nextInt();
        System.out.println("Enter amount:");
        int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter description:");
        String description = scanner.nextLine();
        System.out.println("Enter category id:");
        int categoryId = scanner.nextInt();
        Command command = new CommandDecorator(new OperationCommand(facade, type, id, amount, description, categoryId));
        command.execute();
    }

    private static void showAnalytics(Facade facade, Scanner scanner){
        System.out.println("Analytics");
        LocalDateTime st = LocalDateTime.now().minusYears(1);
        LocalDateTime end = LocalDateTime.now();
        int diff = facade.getBalDiff(st, end);
        System.out.println("Balance (last year)" + diff);
        var category = facade.getCategory(st, end);
        for(var i : category.entrySet()){
            System.out.println(i.getKey() + " " + i.getValue());
        }
    }
    private static void recalcBalance(Facade facade){
        facade.recalc();
    }

    private static void allAccounts(Facade facade){
        List<BankAccount> accountList = facade.getAccounts();
        for(BankAccount account : accountList){
            System.out.println("Id: " + account.getId() + " Name: " + account.getName() + " Balance: " + account.getBalance());
        }
    }

    private static void allCategories(Facade facade){
        List<Category> categories = facade.getCategories();
        for(Category category : categories){
            System.out.println("Id: " + category.getId() + " Name: " + category.getName() + " Type: " + category.getType());
        }
    }


    private static void allOperations(Facade facade){
        List<Operation> operations = facade.getOperations();
        for(Operation operation : operations){
            System.out.println("Id: " + operation.getId() + " Type: " + operation.getType() + " Amount: " + operation.getAmount() +
                    " Date: " + operation.getDate());
        }
    }

    private static void editAccount(Facade facade, Scanner scanner){
        System.out.println("Enter id:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter new name:");
        String name = scanner.nextLine();
        System.out.println("Enter new balance:");
        int bal = scanner.nextInt();
        facade.editAccount(id, name, bal);
    }
    private static void deleteAccount(Facade facade, Scanner scanner){
        System.out.println("Enter id:");
        int id = scanner.nextInt();
        facade.deleteAccount(id);
    }

    private static void editCategory(Facade facade, Scanner scanner){
        System.out.println("Enter id:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter new name:");
        String name = scanner.nextLine();
        facade.editCategory(id, name);
    }
    private static void deleteCategory(Facade facade, Scanner scanner){
        System.out.println("Enter id:");
        int id = scanner.nextInt();
        facade.deleteCategory(id);
    }

    private static void editOperation(Facade facade, Scanner scanner){
        System.out.println("Enter id:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter new description:");
        String name = scanner.nextLine();
        facade.editOperation(id, name);
    }
    private static void deleteOperation(Facade facade, Scanner scanner){
        System.out.println("Enter id:");
        int id = scanner.nextInt();
        facade.deleteOperation(id);
    }
    private static void builderExample(Facade facade){
        if(facade.getAccounts().size() != 0 && facade.getCategories().size() == 0){
            System.out.println("must be account with id 0 & category with id 0");
            return;
        }
        Operation tmp = new OperationBuilder().setId(1).setDescription("car").setType(OperationType.EXPENSE).setAmount(100)
                .setBankAccountId(0).setDate(LocalDateTime.now()).setCategoryId(0).build();
        System.out.println("builder work");
    }
    private static void strategyExample(Facade facade){
        OperationValidation validation = new OperationValidation();
        validation.addStrategy(new AmountValidationStrategy());
        validation.addStrategy(new CategoryValidationStrategy());
        Operation tmp = Factory.createOperation(OperationType.EXPENSE, 0, 0, "qwe", 0);
        if(validation.validate(tmp)){
            System.out.println("Strategy validation work on operation");
        }
    }
}
