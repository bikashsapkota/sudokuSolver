import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by bikash on 10/31/16.
 */
public class createDataset {
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
        neuralNetwork network = new neuralNetwork();
        String directory = "/media/bikash/Study/dataset/";
        File datset = new File(directory);
        int count = 0;
        int[][] gray = new int[81][28*28+1];
        for (String dir: datset.list()) {
            String digDirectory = directory+dir;
            File digDatset = new File(digDirectory);
            for (String dig: digDatset.list()) {
                 String datDir = digDirectory+"/"+dig;

                Img image = new Img(datDir);
                int k = 0;
                gray[count][k] = Integer.parseInt(dir);
                for (int i = 0; i < image.width; i++) {
                    for (int j = 0; j < image.height; j++) {
                        k++;
                        gray[count][k] = image.getGRAY(i,j);
                    }
                }
                count++;
            }
            toCSV(gray);
        }
    }

    public static void toCSV(int[][] data) throws UnsupportedEncodingException, FileNotFoundException {
        PrintWriter writer = new PrintWriter("data1.csv", "UTF-8");
        StringBuilder output = new StringBuilder();
        for (int[] user : data) {
            int i;
            for (i = 0; i<user.length; i++){
                output.append(user[i]).append(",");
            }
            output.append("\n");
            writer.print(output);
        }
        writer.close();
    }

}