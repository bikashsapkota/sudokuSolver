import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by bikash on 10/28/16.
 */
public class Segmentation {
    public static void main(String[] args) {
        Img sImage = new Img("/home/bikash/Desktop/sodukuImage/images/1.png");
        sImage.toNegative();
        sImage.image = sImage.scale(sImage.image,BufferedImage.TYPE_INT_RGB,255,255,(float) 255/sImage.width,(float) 255/sImage.height);
        tiles[][] tiles = markSameBorder(sImage,5);
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                System.out.print(tiles[i][j].tl.x+" "+tiles[i][j].tl.y);
            }
            System.out.println();
        }
        sImage.showImage("test");
    }

    public static tiles[][] markSameBorder(Img image,int borderSize){
        tiles tile[][] = new tiles[9][9];
        int xseed = borderSize;
        int yseed = borderSize;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tile[i][j] = new tiles(image);
                startMark(xseed,5,image,tile[i][j]);
                xseed = tile[i][j].bl.x+borderSize;
            }
            yseed = tile[i][0].tr.y+borderSize;
            xseed=  borderSize;
        }
        startMark(xseed,yseed,image,new tiles(image));
        return tile;
    }

    static void startMark(int i, int j, Img image,tiles tile) {
        ArrayList<pixel> neighBours = image.getNeighbour(i,j);
            for (pixel pix: neighBours) {
                if(pix.x<tile.tl.x){
                    tile.tl.x = pix.x;
                }
                if(pix.y<tile.tl.y){
                    tile.tl.y = pix.y;
                }

                if(pix.x<tile.tr.x){
                    tile.tr.x = pix.x;
                }
                if(pix.y>tile.tr.y){
                    tile.tr.y = pix.y;
                }

                if(pix.x>tile.bl.x){
                    tile.bl.x = pix.x;
                }
                if(pix.y<tile.bl.y){
                    tile.bl.y = pix.y;
                }

                if(pix.x>tile.br.x){
                    tile.br.x = pix.x;
                }
                if(pix.y>tile.br.y){
                    tile.br.y = pix.y;
                }
                startMark(pix.x,pix.y,image,tile);
            }
    }
}

 class tiles{
     pixel tl=new pixel();
     pixel tr=new pixel();
     pixel bl=new pixel();
     pixel br=new pixel();

     tiles(Img image){
         this.tl.x=image.height;
         this.tl.y=image.width;
         this.tr.x=image.height;
         this.tr.y=0;
         this.bl.x=0;
         this.bl.y=image.width;
         this.br.x=image.height;
         this.br.y=0;
     }
}