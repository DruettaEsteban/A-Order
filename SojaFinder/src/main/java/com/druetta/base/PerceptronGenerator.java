/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druetta.base;

import java.util.LinkedList;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author Usuario
 */
public class PerceptronGenerator {
    
    public static MultiLayerPerceptron generatePerceptron(int inputsN, int outputsN, int hiddenL, int hiddenH){
        LinkedList<Integer> perceptDescription = new LinkedList<>();
        perceptDescription.add(inputsN);
        
        for (int i = 0; i < hiddenL; i++) {
            perceptDescription.add(hiddenH);
        }
        
        perceptDescription.add(outputsN);
        
        return new MultiLayerPerceptron(perceptDescription, TransferFunctionType.SIGMOID);
    }
}
