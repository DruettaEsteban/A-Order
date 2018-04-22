/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druetta.base;

import ch.qos.logback.classic.Level;
import com.sun.media.jfxmedia.logging.Logger;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Usuario
 */
public class ImageGenerator {
    
    public static void generateAt(int[][] image, File location, int w, int h) throws IOException{
        
        //Delete down
            BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    out.setRGB(i, j, image[i][j]);
                    
                }
            }
            
            NameGenerator name = new NameGenerator("png", location);
            
            File stream = new File(location.toString()+ "\\" + name.dropName());
            
            System.out.println(stream.createNewFile());
            ImageIO.write(out, "png", stream); 
    }
    
    /*
    public static int[][] normalize(int[][] image, int w, int h) throws IOException{
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                out.setRGB(i, j, image[i][j]);

            }
        }
        
        File tempFolder = File.createTempFile("Normalized", ".png", null);
        ImageIO.write(out, "png", tempFolder);
        
        ByteImage toReturn = new ByteImage(tempFolder);
        try{
            tempFolder.delete();
            tempFolder.deleteOnExit();
        }catch(Exception e){
            Logger.logMsg(Level.WARN_INT, "ProgrammingWarning");
        }
        return toReturn.toArray();
    }
    */
    
    
}
