import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import static com.sun.corba.se.impl.util.RepositoryId.cache;

class SwingBasic extends JFrame
{
    JLabel l;
    public void start()
    {
        Container cp = getContentPane();
        l=new JLabel();
        l.setText("Name :");
        final JFileChooser image=new JFileChooser();

        add(image);
        image.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        File file = image.getSelectedFile();
                        sudokuSegmantation test=new sudokuSegmantation(file);
                        try{
                            int[][] datas = test.start(file);
                            sudoku s1 = new sudoku(datas);

                            sodukuMultiple tests = new sodukuMultiple();
                            sudoku s2 = tests.recursion(s1);
                            //System.out.println(file);
                            //tests.printBoard(s2);
                            BufferedImage img = ImageIO.read(file);
                            BufferedImage newImg = new BufferedImage(250,250,BufferedImage.TYPE_INT_RGB);
                            //img = scale(img,BufferedImage.TYPE_INT_RGB,250,250,1,1);
                            newImg.createGraphics().drawImage(img, 0, 0, 250, 250, null);

                            int width = newImg.getWidth();
                            int height = newImg.getHeight();
                            showImage(newImg,"Sudoku Solution");
                            //System.out.println(width);
                            //System.out.println(height);

                            datas = test.start(file);
                            Graphics graphics = newImg.getGraphics();
                            graphics.setColor(Color.RED);
                            //graphics.drawString("adsfdfs",12,12);
                            //Graphics2D g2d = img.createGraphics();
                            //g2d.drawString("a",12,12);
                            //showImage(newImg,"tes");

                            for (int i = 0; i < 9; i++) {
                                for (int j = 0; j < 9; j++) {
                                    //System.out.println(datas[i][j]);
                                    if(datas[i][j]==0){
                                        graphics.drawString(s2.get(i,j)+"",height/9*(j+1)-10,width/9*(i+1)-10);
                                    }
                                }
                                //System.out.println();
                            }


                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
        );
        setLayout(new FlowLayout());
        setName("Sudoku Solver");
        setSize(800,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static void main(String args[])
    {
        new SwingBasic().start();
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
