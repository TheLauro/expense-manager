package com.studies;

import java.util.TreeSet;
import java.util.Set;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Category {

    private static final Set<String> allCategories = new TreeSet<>(Arrays.asList("Alimentacao","Transporte","Entretenimento","Aluguel"));

    private Category() {

    }

    public static void addCategories(String newCategory) {
        if(newCategory == null || newCategory.trim().isEmpty()){
            throw new IllegalArgumentException("Categoria não pode ser vazia ou nula!");
        }
        
        String formatedCategory = newCategory.trim();

        boolean alreadyRegistered = allCategories.add(formatedCategory);
        
        if(!alreadyRegistered){
            throw new IllegalArgumentException("Categoria já registrada!");
        } 
    }

    public static boolean exists(String category){
        
        if(category == null || category.trim().isEmpty()){
            return false;
        }

        return allCategories.contains(category.trim());
    }

    public static List<String> getCategories() {
        return Collections.unmodifiableList(new ArrayList<>(allCategories));
    }

    
}
