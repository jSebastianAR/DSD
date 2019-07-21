/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorrmi_j1;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
 *
 * @author jSeba
 */
public class DisplayImage extends JFrame{

    public ImageIcon DisplayImage(String nombreImagen) throws IOException
    {
        // C:\Users\jSeba\Desktop\cartas               El usuario debe cambiarse dependiendo de la PC
        BufferedImage img=ImageIO.read(new File("C:\\Users\\jSeba\\Desktop\\cartas\\"+nombreImagen+".png"));                  
        Image nImage = getScaledImage(img,100,100);
        ImageIcon icon=new ImageIcon(nImage);
        return icon;
    }
    
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
}