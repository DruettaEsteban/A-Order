/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druetta.base;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 *
 * @author Usuario
 */
class NameGenerator {
    private String lastName;
    private final String extension;
    private final File location;
    
    NameGenerator(String extension, File location){
        this.extension = "." + extension;
        lastName = "0";
        this.location = location;
    }
    
    public String dropName(){
        String toReturn = lastName;
        lastName = String.valueOf(Integer.parseInt(lastName) + 1);
        HashSet<String> names = new HashSet(Arrays.stream(location.list()).collect(Collectors.toSet()));
        boolean contains = false;
        Iterator iterador = names.iterator();
        while(iterador.hasNext()){
            String name = String.valueOf(iterador.next());
            if(name.contains(toReturn)){
                contains = true;
                break;
            }
        }
        
        if(contains){
            System.out.println("pero...");
            return dropName();
        }
        
        return toReturn + extension;
    }
    
}
