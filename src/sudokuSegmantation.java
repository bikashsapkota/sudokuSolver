/**
 * Created by bikash on 10/25/16.
 */
import Jama.Matrix;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by bikash on 10/19/16.
 */

public class sudokuSegmantation {
    BufferedImage image;
    int width;
    int height;

    public sudokuSegmantation(File input) {

        try {
            image = ImageIO.read(input);
            //image = scale(image,BufferedImage.TYPE_INT_RGB,255,255,1,1);
            width = image.getWidth();
            height = image.getHeight();

            for(int i=0; i<height; i++){
                for(int j=0; j<width; j++){

                    Color c = new Color(image.getRGB(j, i));
                    int red = (int)(c.getRed() * 0.299);
                    int green = (int)(c.getGreen() * 0.587);
                    int blue = (int)(c.getBlue() *0.114);
                    int rgb = red+green+blue;

                    rgb=rgb>150?0:255;
                    Color newColor = new Color(rgb,rgb,rgb);

                    image.setRGB(j,i,newColor.getRGB());
                }
            }
        } catch (Exception e) {}
    }

    public static int[][] start(File input) throws Exception {
        //GrayScale obj = new GrayScale();
        sudokuSegmantation obj = new sudokuSegmantation(input);
        neuralNetwork network = new neuralNetwork();
        BufferedImage image = obj.image;
        int[] horizontalSliceIndex;
        horizontalSliceIndex = horizontalSliceIndex(image,9);
        int[] verticalSliceIndex;
        verticalSliceIndex = verticalSliceIndex(image,9);
        BufferedImage numTiles[][] = imagetomatrix(ImageIO.read(input),horizontalSliceIndex,verticalSliceIndex,9);
        //BufferedImage numTiles[][] = imagetomatrix(image,horizontalSliceIndex,verticalSliceIndex,9);
        //BufferedImage readyTiles[][] = new BufferedImage[numTiles.length][numTiles.length];
        Color newColor = new Color(0,0,0);

        for (int i = 0; i < numTiles.length-1; i++) {
            for (int j = 0; j < numTiles.length-1; j++) {
                for (int k = 0; k < numTiles[i][j].getWidth(); k++) {
                    numTiles[i][j].setRGB(k,0,newColor.getRGB());
                    numTiles[i][j].setRGB(k,1,newColor.getRGB());
                    numTiles[i][j].setRGB(k,2,newColor.getRGB());
                    numTiles[i][j].setRGB(k,3,newColor.getRGB());
                }

                for (int k = 0; k < numTiles[i][j].getHeight(); k++) {
                    numTiles[i][j].setRGB(0,k,newColor.getRGB());
                    numTiles[i][j].setRGB(1,k,newColor.getRGB());
                    numTiles[i][j].setRGB(2,k,newColor.getRGB());
                    numTiles[i][j].setRGB(3,k,newColor.getRGB());
                }

                for (int k = 0; k < numTiles[i][j].getHeight(); k++) {
                    int temp = numTiles[i][j].getWidth();
                    numTiles[i][j].setRGB(temp-1,k,newColor.getRGB());
                    numTiles[i][j].setRGB(temp-2,k,newColor.getRGB());
                    numTiles[i][j].setRGB(temp-3,k,newColor.getRGB());
                    numTiles[i][j].setRGB(temp-4,k,newColor.getRGB());
                }

                for (int k = 0; k < numTiles[i][j].getWidth(); k++) {
                    int temp = numTiles[i][j].getHeight();
                    numTiles[i][j].setRGB(k,temp-1,newColor.getRGB());
                    numTiles[i][j].setRGB(k,temp-2,newColor.getRGB());
                    numTiles[i][j].setRGB(k,temp-3,newColor.getRGB());
                    numTiles[i][j].setRGB(k,temp-4,newColor.getRGB());
                }
                //System.out.println(numTiles[i][j].getHeight()+":"+numTiles[i][j].getWidth()+":"+i+":"+j);
                //Tesseract instance = Tesseract.getInstance();
                //System.out.print(instance.doOCR(numTiles[i][j]));
                File outputfile = new File(i+""+j+"image.jpg");
                ImageIO.write(numTiles[i][j],"jpg",outputfile);
            }
            //System.out.println();
        }

        int k;
        Matrix imgLinear = new Matrix(784,1);
        //System.out.println(numTiles[0][1].getHeight()*numTiles[0][1].getWidth());
        //int row =0;
        //int col = 1;
        int[][] datas= new int[9][9];
        for (int col = 0; col<9; col++){
            for (int row=0; row<9; row++){
                k=0;
                for (int i=0; i<numTiles[row][col].getHeight(); i++){
                    for (int j = 0; j < numTiles[row][col].getWidth(); j++) {
                        Color c = new Color(numTiles[row][col].getRGB(i, j));
                        float temp = (float) (((c.getBlue()*0.114+c.getRed()*0.299+c.getGreen()*0.587)/255)*0.99+0.01);
                        //System.out.print(numTiles[row][col].getRGB(j, i));
                        imgLinear.set(k,0,temp);
                        k++;
                    }
                }

     //           showImage(numTiles[row][col],"test");
                datas[col][row] = network.predict(imgLinear,network);
                //System.out.print(datas[col][row]+" ");
            }
            //System.out.println();
        }
        return datas;
    }


    public static int[] horizontalSliceIndex(BufferedImage image, int size){
        int[] horizontalIndex = new int[size+1];
        int index = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            int count=0;
            for (int j = 0; j < image.getHeight(); j++) {
                if(image.getRGB(j,i)==-1){
                    count++;
                }
            }

            if(count>image.getHeight()-23){
                horizontalIndex[index] = i;
                index=index+1;
                i=i+3;
            }
        }
        return horizontalIndex;
    }

    public static int[] verticalSliceIndex(BufferedImage image, int size){
        int[] verticalIndex = new int[size+1];
        int index = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            int count=0;
            for (int j = 0; j < image.getWidth(); j++) {
                if(image.getRGB(i,j)==-1){
                    count++;
                }
            }

            if(count>image.getHeight()-23){
                verticalIndex[index] = i;
                index++;
                i=i+3;
            }
        }
        return verticalIndex;
    }

    public static BufferedImage[][] imagetomatrix(BufferedImage image, int[] hSlice, int[] vSlice, int size) throws IOException {
        BufferedImage[][] tiles = new BufferedImage[size+1][size+1];
        BufferedImage[][] resizeTile = new BufferedImage[size+1][size+1];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j]=new BufferedImage(hSlice[j+1]-hSlice[j],vSlice[j+1]-vSlice[j], BufferedImage.TYPE_BYTE_GRAY);
                for (int k = 0; k < tiles[i][j].getHeight()-1; k++) {
                    for (int l = 0; l < tiles[i][j].getWidth()-1; l++) {
                        Color c = new Color(image.getRGB(hSlice[i]+l,vSlice[j]+k));
                        int red = (int)(c.getRed() * 0.299);
                        int green = (int)(c.getGreen() * 0.587);
                        int blue = (int)(c.getBlue() *0.114);
                        int rgb = red+green+blue;

                        //rgb=255-rgb;
                        rgb=rgb>145?0:255;
                        Color newColor = new Color(rgb,rgb,rgb);
                        tiles[i][j].setRGB(l,k,newColor.getRGB());
                    }
                }
                resizeTile[i][j] = scale(tiles[i][j],BufferedImage.TYPE_INT_RGB,28,28,1,1);
                //resizeTile[i][j].createGraphics().drawImage(tiles[i][j], 0, 0, 28, 28, null);
                File outputfile = new File(i+""+j);
                //ImageIO.write(resizeTile[i][j], "png", outputfile);

            }
        }

        return resizeTile;
    }

    public static void showImage(BufferedImage image, String name){
        JFrame frame = new JFrame(name);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setSize(400,500);
        frame.setVisible(true);
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
}