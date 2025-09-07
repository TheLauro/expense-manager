package com.studies;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Category {

    public static final List<String> allCategories = new ArrayList<>();

    private Category() {

    }

    public static void addCategories(String newCategory) {
        if(newCategory == null || newCategory.trim().isEmpty()){
            throw new IllegalArgumentException("Categoria não pode ser vazia ou nula!");
        }
        
        String formatedCategory = newCategory.trim();
        
        if(allCategories.contains(formatedCategory)){
            throw new IllegalArgumentException("Categoria já registrada!");
        }
        
        allCategories.add(formatedCategory);
    }

    public static List<String> getCategories() {
        return Collections.unmodifiableList(allCategories);
    }
}
