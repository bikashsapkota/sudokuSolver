/**
 * Created by bikash on 2/14/17.
 */
 import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by bikash on 10/28/16.
 */
public class Maze {
    public static boolean temp = false;
    public static void main(String[] args) {
        //Img sImage = new Img("/home/bikash/Desktop/sodukuImage/images/1.png");
        Img sImage = new Img("/home/bikash/Desktop/sodukuImage/images/maze.gif");
        sImage.image = sImage.scale(sImage.image,BufferedImage.TYPE_INT_RGB,175,175,(float) 175/sImage.width,(float) 175/sImage.height);
        mark(sImage,2,2);
        sImage.showImage("test");
    }

    public static int mark(Img img, int x, int y){
        if(x==174&&y==174){
            temp = true;
        }
        if(y>=img.image.getHeight()-2||x>=img.image.getWidth()-2||x<2||y<2)
            return 0;

        img.image.setRGB(x,y,new Color(255,0,0).getRGB());
            //img.setGray(x,y,124);

        if(img.getGRAY(x+1,y)>125){
             mark(img,x+1,y);
        }
        if(img.getGRAY(x,y+1)>125){
            return mark(img,x,y+1);
        }
        if(img.getGRAY(x-1,y)>125){
            return mark(img,x-1,y);
        }
        if(img.getGRAY(x,y-1)>125){
           return mark(img,x,y-1);
        }


        if(temp) {
            img.image.setRGB(x, y, new Color(0, 255, 0).getRGB());
            //System.out.println(x+":"+y);
        }
        return 0;

    }

}

