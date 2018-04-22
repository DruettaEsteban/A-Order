
import com.druetta.base.ByteImage;
import java.io.File;
import java.io.IOException;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class testExceptions {
    @Test
    public void testImageGenerator() throws IOException{
        File f = new File("C:\\testing\\finalClassic\\0.png");
        ByteImage image = new ByteImage(f);
        image.scale(100, 200);
    }
    
    
}
