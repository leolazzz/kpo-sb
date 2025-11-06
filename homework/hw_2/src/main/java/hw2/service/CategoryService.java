package hw2.service;


import hw2.factory.Factory;
import hw2.model.BankAccount;
import hw2.model.Category;
import hw2.model.OperationType;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private List<Category> categories;
    public CategoryService(){
        this.categories = new ArrayList<>();
    }
    public Category createCategory(OperationType type, String desc){
        Category category = Factory.createCategory(type, desc);
        categories.add(category);
        return category;
    }
    public List<Category> getCategories(){
        return new ArrayList<>(categories);
    }

    public Category getCategoryById(int id){
        for(Category category : categories){
            if(category.getId() == id){
                return category;
            }
        }
        throw new IllegalArgumentException("id must be in list");
    }
    public String getCategoryNameById(int id){
        for(Category category : categories){
            if(category.getId() == id){
                return category.getName();
            }
        }
        throw new IllegalArgumentException("id must be in list");
    }
    public void updateCategory(int id, String name){
        for(Category category : categories){
            if(category.getId() == id){
                category.setName(name);
            }
        }
    }

    public void deleteCategory(int id){
        categories.removeIf(category -> category.getId() == id);
    }

}
