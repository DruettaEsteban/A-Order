/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druetta.base;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.LinkedList;

import javax.imageio.ImageIO;

/**
 *
 * @author Usuario
 */
public class ByteImage {
    
    BufferedImage imageBuf;
    
    public ByteImage(File image) throws IOException{
        BufferedImage bufImage = ImageIO.read(image);
        this.imageBuf = bufImage;
        
    }
    private int locationCounter = 0;
    
    private int[][] toArray(BufferedImage bufImage){
        int[][] image = new int[bufImage.getWidth()][bufImage.getHeight()];
        int h = bufImage.getHeight();
        int w = bufImage.getWidth();

        
        for(int i = 0 ; i < h ; i++){
            for (int j = 0; j < w; j++) {
                image[j][i] = bufImage.getRGB(j, i);
            }
        }
        return image;
    }
    
    public int[][] toArray(){
        BufferedImage bufImage = imageBuf;
        int[][] image = new int[bufImage.getWidth()][bufImage.getHeight()];
        int h = bufImage.getHeight();
        int w = bufImage.getWidth();

        
        for(int i = 0 ; i < h ; i++){
            for (int j = 0; j < w; j++) {
                image[j][i] = bufImage.getRGB(j, i);
            }
        }
        return image;
    }
    
    public int[][] scale(int targetWidth, int targetHeight) throws IOException {
        BufferedImage img = this.imageBuf;
        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        BufferedImage scratchImage = null;
        Graphics2D g2 = null;

        int weith = img.getWidth();
        int height = img.getHeight();

        int prevW = weith;
        int prevH = height;

        do {
            if (weith > targetWidth) {
                weith /= 2;
                weith = (weith < targetWidth) ? targetWidth : weith;
            }

            if (height > targetHeight) {
                height /= 2;
                height = (height < targetHeight) ? targetHeight : height;
            }

            if (scratchImage == null) {
                scratchImage = new BufferedImage(weith, height, type);
                g2 = scratchImage.createGraphics();
            }

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, weith, height, 0, 0, prevW, prevH, null);

            prevW = weith;
            prevH = height;
            ret = scratchImage;
        } while (weith != targetWidth || height != targetHeight);

        if (g2 != null) {
            g2.dispose();
        }

        if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
            scratchImage = new BufferedImage(targetWidth, targetHeight, type);
            g2 = scratchImage.createGraphics();
            g2.drawImage(ret, 0, 0, null);
            g2.dispose();
            ret = scratchImage;
        }
        //DELETE DOWN
        ImageGenerator.generateAt(toArray(ret), new File("C:\\testing\\revolution"), ret.getWidth(), ret.getHeight());
    return toArray(ret);

    }
    
    public double[][] specialScalar(int targetWidth, int targetHeight) throws IOException{
        int[][] byteImage = scale(targetWidth, targetHeight);
        double[][] target = new double[targetWidth][targetHeight];
        
        
        
        for (int i = 0; i < targetHeight; i++) {
            for (int j = 0; j < targetWidth; j++) {
                
                BigInteger minime = new BigInteger(Integer.toString(Integer.MAX_VALUE));
                

                BigDecimal divisor = new BigDecimal(minime.add(new BigInteger("2107842860")));
                
                BigDecimal dividendo = new BigDecimal(String.valueOf(byteImage[j][i]+minime.intValue()));
           
                System.out.println(dividendo+"/"+divisor);
                
                if (dividendo.intValue() > max) max = dividendo.intValue();
                if(dividendo.intValue() < min) min = dividendo.intValue();
                System.out.println("divisor "+divisor);
                System.out.println("dividendo"+dividendo);
                
                
                BigDecimal result = dividendo.divide(divisor, 18, RoundingMode.HALF_UP);
                
                System.out.println("r"+result);
                
                target[j][i] = result.doubleValue();
                
            }
            
        }
        ImageGenerator.generateAt(byteImage, new File("C:\\testing\\revolution"), targetWidth, targetHeight);
        return target;
    } 
    
    private static int max = Integer.MIN_VALUE;
    private static int min  = Integer.MAX_VALUE;
    
    public double[] simplificatorScalar(int targetWidth, int targetHeight) throws IOException{
        double[][] result = specialScalar(targetWidth, targetHeight);
        
        LinkedList<Double> image = new LinkedList<>();
        //Check dimensions
        
        int[][] d = new int[targetWidth][targetHeight];
        //Store
        for (int i = 0; i < targetHeight; i++) {
            for (int j = 0; j < targetWidth; j++) {
                image.add(result[j][i]);
            }
        }
        
        double[] entrySet = new double[image.size()];
        //Stupid Casting
        image.stream()
              .map(v -> v.toString())
                                       .forEach(
                                               (v) -> {
                                                   entrySet[locationCounter] = Double.parseDouble(v);
                                                   locationCounter++;
                                               });
        locationCounter = 0;
            
        return entrySet;
    }
    
}
