/**
 * Created by bikash on 10/27/16.
 */



import java.io.*;
import net.sourceforge.tess4j.*;

public class tessaract {
    public static void main(String[] args) {
        File imageFile = new File("<path of your image>");
        Tesseract instance = Tesseract.getInstance(); //

        try {

            String result = instance.doOCR(imageFile);
            System.out.println(result);

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
