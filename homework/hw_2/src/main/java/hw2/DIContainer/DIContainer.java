package hw2.DIContainer;

import hw2.export.ExportVisitor;
import hw2.export.JsonExport;
import hw2.model.BankAccount;
import hw2.service.*;

import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private Map<Class<?>, Object> mp = new HashMap<>();
    public DIContainer(){
        initialize();
    }
    private void initialize(){
        BankAccountService accountService = new BankAccountService();
        CategoryService categoryService = new CategoryService();
        OperationService operationService = new OperationService(accountService);
        AnalyticsService analyticsService = new AnalyticsService(operationService, accountService);
        ExportVisitor exportVisitor = new JsonExport();

        mp.put(BankAccountService.class, accountService);
        mp.put(CategoryService.class, categoryService);
        mp.put(OperationService.class, operationService);
        mp.put(AnalyticsService.class, analyticsService);
        mp.put(ExportVisitor.class, exportVisitor);

        Facade facade = new Facade(accountService, categoryService, operationService, analyticsService);
        mp.put(Facade.class, facade);
    }
    @SuppressWarnings("unchecked")
    public <T> T getmp(Class<T> type){
        Object tmp = mp.get(type);
        return (T) tmp;
    }
}
