import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by bikash on 10/28/16.
 */
public class Img {
    Scanner test = new Scanner(System.in);
    BufferedImage image;
    int width;
    int height;
    ArrayList<pixel> borders=new ArrayList<>();
    int threshold = 50;
    int r = -65536;

     Img(String path) {
        try {
            File input = new File(path);
            image = ImageIO.read(input);
            //image = scale(image,BufferedImage.TYPE_INT_RGB,255,255,0.55,0.55);
            width  = image.getWidth();
            //System.out.println(width);
            height=image.getHeight();
            //System.out.println(height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showImage(String name){
        JFrame frame = new JFrame(name);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(this.image)));
        frame.pack();
        frame.setSize(400,500);
        frame.setVisible(true);
    }

    int getGRAY(int i, int j){
        //System.out.println("Error "+i+" "+j);
        Color c = new Color(image.getRGB(i, j));
        int red = (int)(c.getRed() * 0.299);
        int green = (int)(c.getGreen() * 0.587);
        int blue = (int)(c.getBlue() *0.114);
        return red+green+blue;
    }

    void setGray(int i, int j, int value){
        Color c = new Color(value,value,value);
        this.image.setRGB(i,j,c.getRGB());
    }

    public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;
        if(sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, imageType);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }

    ArrayList<pixel> getNeighbour(int i,int j){
        ArrayList<pixel> neighBours = new ArrayList<pixel>();
            if(i>0){
            if(this.getGRAY(i-1,j)<=this.threshold && this.image.getRGB(i-1,j)!=this.r){
                pixel temp=new pixel();
                this.image.setRGB(i-1,j,this.r);
                temp.x = i-1;
                temp.y = j;
                neighBours.add(temp);
                //System.out.println("test1");
            }}
            if(i<this.width-1){
            if(this.getGRAY(i+1,j)<=this.threshold && this.image.getRGB(i+1,j)!=this.r){
                pixel temp=new pixel();
                temp.x = i+1;
                temp.y = j;
                this.image.setRGB(i+1,j,this.r);
                neighBours.add(temp);
                //System.out.println("test2");
            }}
            if(j>0){
            if(this.getGRAY(i,j-1)<=this.threshold && this.image.getRGB(i,j-1)!=this.r){
                pixel temp=new pixel();
                temp.x = i;
                temp.y = j-1;
                this.image.setRGB(i,j-1,this.r);
                neighBours.add(temp);
                //System.out.println("test3");
            }}
            if(j<this.height-1){
            if(this.getGRAY(i,j+1)<=this.threshold && this.image.getRGB(i,j+1)!=this.r){
                pixel temp=new pixel();
                temp.x = i;
                temp.y = j+1;
                neighBours.add(temp);
                this.image.setRGB(i,j+1,this.r);
                //System.out.println("test4");
            }}
            /*
            if(i>0&&j<this.height-1){
            if(this.getGRAY(i-1,j+1)<=this.threshold && this.image.getRGB(i-1,j+1)!=this.r){
                pixel temp=new pixel();
                temp.x = i-1;
                temp.y = j+1;
                neighBours.add(temp);
                this.image.setRGB(i-1,j+1,this.r);
            }}
            if(i<this.height-1&&j>0){
            if(this.getGRAY(i+1,j-1)<=this.threshold && this.image.getRGB(i+1,j-1)!=this.r){
                pixel temp=new pixel();
                temp.x = i+1;
                temp.y = j-1;
                neighBours.add(temp);
                this.image.setRGB(i+1,j-1,this.r);
            }}
            if(i<this.width-1&&j<this.height-1){
            if(this.getGRAY(i+1,j+1)<=this.threshold && this.image.getRGB(i+1,j+1)!=this.r){
                pixel temp=new pixel();
                temp.x = i+1;
                temp.y = j+1;
                neighBours.add(temp);
                this.image.setRGB(i+1,j+1,this.r);
            }}
            if(i>0&&j>0){
            if(this.getGRAY(i-1,j-1)<=this.threshold && this.image.getRGB(i-1,j-1)!=this.r){
                pixel temp=new pixel();
                temp.x = i-1;
                temp.y = j-1;
                neighBours.add(temp);
                this.image.setRGB(i-1,j-1,this.r);
            }}*/

            return neighBours;
    }

    public void toNegative(){
        for (int i = 0; i < this.image.getWidth(); i++) {
            for (int j = 0; j < this.image.getHeight(); j++) {
                this.setGray(i,j,255-this.getGRAY(i,j));
            }
        }
    }
}




class pixel{
    int x;
    int y;
}
