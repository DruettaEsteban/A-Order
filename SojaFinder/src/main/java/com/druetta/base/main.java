/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druetta.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.random.WeightsRandomizer;
/**
 *
 * @author Usuario
 */
public class main implements LearningEventListener{
    //hidden
    private static final int HIDDEN_H = 1;
    private static final int HIDDEN_L = 5;
    //inputs
    private static final int INPUT_H = 10000;
    //outputs
    private static final int OUTPUT_H = 1; 
    
    private static final int TRAINING_CICLES = 20;
    private static File perceptronLocation;
    private static final float LEARNING_RATE = 0.0001f;
    
    private static MultiLayerPerceptron net;
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    
    //C:\testing\NetIn
    //C:\testing\NetIn\557px-Star_red.svg.png

    
    public static void main(String[] args) throws IOException, ClassNotFoundException, FileNotFoundException, InterruptedException {
        new main().run();
    }
    
    private static void serializeAndClose() throws FileNotFoundException, IOException{
        
        net.save(perceptronLocation.toString());
        System.exit(0);
        
    }
    
    private static void train(File traningSetLocation, int trainingCicles){
        ArrayList<String> names = new ArrayList(Arrays.stream(traningSetLocation.list()).collect(Collectors.toList()));
        LinkedHashSet<File> directories = new LinkedHashSet(Arrays.stream(traningSetLocation.listFiles()).map((File v) -> 
        {   names.add(v.getName());
            return v;
        }).collect(Collectors.toList()));
        
        Iterator ip = directories.iterator();
        int counter = 0;
        
        
        
        ArrayList<LinkedList<Double>> images = new ArrayList<>();  
        
        directories.forEach((File f) -> {
            try {
                ByteImage image = new ByteImage(f);
                LinkedList<Double> storedImage = new LinkedList<>();
                //Check dimensions
                int x = 100;
                int y = 100;
                double[][] imageArray = image.specialScalar(x, y);
                int[][] d = new int[x][y];
                
                //Store
                for (int i = 0; i < y; i++) {
                    for (int j = 0; j < x; j++) {
                        storedImage.add(imageArray[j][i]);
                    }
                }
                
                //Big Store
                images.add(storedImage);
                
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        System.out.println("marker");
        
        DataSet trainingSet = new DataSet(INPUT_H, OUTPUT_H);
        int nameSelector = 0;
        System.out.println(images.size());
        for (LinkedList image : images) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Y or N: " + names.get(nameSelector));
            String answer = scan.next().toLowerCase();
            nameSelector++;
            
            
            double[] entrySet = new double[image.size()];
                    //Stupid Casting
                    image.stream()
                          .map(v -> v.toString())
                                                   .forEach(
                                                           (v) -> {
                                                               entrySet[locationCounter] = Double.parseDouble(v.toString());
                                                               locationCounter++;
                                                           });
                    locationCounter = 0;
            
            switch(answer){
                case "y":
                    trainingSet.addRow(new DataSetRow(entrySet, new double[]{1}));
                    break;
                case "n":
                    trainingSet.addRow(new DataSetRow(entrySet, new double[]{0}));
                    break;
                default:
                    System.err.close();
            }
        }
        
        BackPropagation bk = new BackPropagation();
        bk.setMaxIterations(trainingCicles);
        net.learn(trainingSet, bk);
        
    }
    private static boolean continueTraining = false;
    private static void train(File traningSetLocation){
        ArrayList<String> names = new ArrayList(Arrays.stream(traningSetLocation.list()).collect(Collectors.toList()));
        LinkedHashSet<File> directories = new LinkedHashSet(Arrays.stream(traningSetLocation.listFiles()).map((File v) -> 
        {   names.add(v.getName());
            return v;
        }).collect(Collectors.toList()));
        
        Iterator ip = directories.iterator();
        int counter = 0;
        
        
        
        ArrayList<LinkedList<Double>> images = new ArrayList<>();  
        
        directories.forEach((File f) -> {
            try {
                ByteImage image = new ByteImage(f);
                LinkedList<Double> storedImage = new LinkedList<>();
                //Check dimensions
                int x = 100;
                int y = 100;
                double[][] imageArray = image.specialScalar(x, y);
                int[][] d = new int[x][y];
                
                //Store
                for (int i = 0; i < y; i++) {
                    for (int j = 0; j < x; j++) {
                        storedImage.add(imageArray[j][i]);
                    }
                }
                
                //Big Store
                images.add(storedImage);
                
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        System.out.println("marker");
        
        DataSet trainingSet = new DataSet(INPUT_H, OUTPUT_H);
        int nameSelector = 0;
        System.out.println(images.size());
        for (LinkedList image : images) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Y or N: " + names.get(nameSelector));
            String answer = scan.next().toLowerCase();
            nameSelector++;
            
            
            double[] entrySet = new double[image.size()];
                    //Stupid Casting
                    image.stream()
                          .map(v -> v.toString())
                                                   .forEach(
                                                           (v) -> {
                                                               entrySet[locationCounter] = Double.parseDouble(v.toString());
                                                               locationCounter++;
                                                           });
                    locationCounter = 0;
            
            switch(answer){
                case "y":
                    trainingSet.addRow(new DataSetRow(entrySet, new double[]{1}));
                    break;
                case "n":
                    trainingSet.addRow(new DataSetRow(entrySet, new double[]{0}));
                    break;
                default:
                    System.err.close();
            }
        }
        
        continueTraining = true;
        BackPropagation bk = new BackPropagation();
        
        net.learn(trainingSet, bk);
        
        
    }
    
    //BEGINNING
    private void run() throws FileNotFoundException, IOException, ClassNotFoundException, InterruptedException{
        perceptronLocation = new File(System.getProperty("user.dir")+"\\perceptron.nnet");
        if(!perceptronLocation.exists()){ 
            net = PerceptronGenerator.generatePerceptron(INPUT_H, 1, HIDDEN_L, HIDDEN_H);
            net.randomizeWeights(new WeightsRandomizer(new Random(123)));
            net.save(perceptronLocation.toString());
            System.out.println("a");
        }else{
            net = (MultiLayerPerceptron) MultiLayerPerceptron.createFromFile(perceptronLocation);
        }
        
        
        Scanner scan = new Scanner(System.in);
        System.out.print("Localizacion conjunto entrenamiento: ");
        
        //train
        net.getLearningRule().setLearningRate(LEARNING_RATE);
        File trainingLocation = new File("C:\\testing\\NetIn");
        
        Thread train = new Thread(() -> {
            train(trainingLocation);
        });
        train.start();
        
        //train(TRAINING_CICLES, trainingLocation);
        while(!continueTraining){
            Thread.sleep(1000);
        }
        
        //stopControl
        Thread ask = new Thread(() -> {
            System.out.println("Wanna stop?");
            Scanner scann = new Scanner(System.in);
            scann.next();
        });
        ask.start();
        
        while(true){
            Thread.sleep(1500);
            if(!train.isAlive()){
                break;
            }else if(!ask.isAlive()){
                System.out.println("Stopped!");
                net.stopLearning();
                break;
            }
        }
        
        System.out.print("Localizacion archivo a analizar: ");
        
        
        System.out.println("Result: "+analize(new File(scan.next())));
        serializeAndClose();
    }
   

    
    private static double analize(File targetLocation) throws IOException{
        ByteImage image = new ByteImage(targetLocation);
        net.setInput(image.simplificatorScalar(100, 100));
        net.calculate();
        double[] outs;
        outs = net.getOutput();
        return outs[0];
    }
    
    
    private static void train(int cicles, File location){
        train(location, cicles);
    }
    
        public static int locationCounter = 0;

    @Override
    public void handleLearningEvent(LearningEvent event) {
        
    }

    
}
