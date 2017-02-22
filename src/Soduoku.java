import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.StrictMath.min;

/**
 * Created by bikash on 9/5/16.
 */
class Soduoku {
    int dimension;

    private int[][] cell;

    Soduoku(int[][] cell){
        this.cell = cell;
        this.dimension = cell.length;
    }
    void set(int i,int j, int value){
        this.cell[i][j] = value;
    }

    int get(int i,int j){
        return this.cell[i][j];
    }

    void printBoard(){
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(this.get(i,j)+" ");
            }
            System.out.println();
        }
    }

    Boolean isComplete(){
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(this.cell[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }

    void leastPossible(){
        int min = this.dimension;
        int min1;
        int x=this.dimension;
        int y=this.dimension;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if(this.get(i,j)==0){
                min1 = min(min,this.probablenum(i,j).size());
                if (min1<min){
                    min = min1;
                    x = i;
                    y = j;
                }
            }}
        }
        int value = (int) this.probablenum(x,y).get(0);
        //System.out.println("x="+x+"y="+y+"value="+value);
        this.set(x,y, value);
    }

    List probablenum(int i, int j){
        List<Integer> entry=new ArrayList<>();
        for (int k = 0; k < this.dimension; k++) {
            entry.add(k+1);
        }


        for (int k=0; k<this.dimension; k++){
            entry.removeAll(Arrays.asList(this.cell[k][j]));}

        for (int k=0; k<this.dimension; k++)
            entry.removeAll(Arrays.asList(this.cell[i][k]));
        return entry;
    }

}

class Solution{
   public static void main(String[] args) {
       int[][] cell = {
               {0,3,9,6,7,4,8,0,0},
               {6,0,0,1,9,5,0,0,0},
               {1,0,8,2,3,0,0,6,0},
               {8,0,1,0,6,0,0,0,3},
               {4,0,0,8,5,3,6,0,1},
               {7,1,0,9,2,0,0,0,6},
               {0,6,0,0,0,0,2,8,0},
               {0,0,0,4,1,9,0,0,5},
               {3,0,4,0,8,0,0,7,9}
       };
       Soduoku s1 = new Soduoku(cell);

       while(!s1.isComplete()){
           s1.leastPossible();
       }
       s1.printBoard();

   }
}