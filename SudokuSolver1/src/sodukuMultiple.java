import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static java.lang.StrictMath.min;

/**
 * Created by bikash on 9/5/16.
 */
class sudoku {
    int dimension;
    private int[][] cell = {
            {-1,-1,-1,3,-1,5},
            {-1,3,-1,-1,-1,2},
            {1,2,-1,-1,4,3},
            {6,-1,-1,-1,-1,4},
            {2,1,-1,4,-1,6},
            {3,4,5,-1,-1,-1}
    };

    sudoku(int dim){
        dimension=dim;
    }
    void set(int i,int j, int value){
        this.cell[i][j] = value;
    }

    int get(int i,int j){
        return this.cell[i][j];
    }


    Boolean isComplete(){
        //System.out.println("iscomplete called");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(this.cell[i][j]==-1){
                    return false;
                }
            }
        }
        return true;
    }



    List probablenum(int i, int j){
        List<Integer> entry=new ArrayList<>(Arrays.asList(1,2,3,4,5,6));

        for (int k=0; k<this.dimension; k++)
            entry.removeAll(Arrays.asList(this.cell[k][j]));

        for (int k=0; k<this.dimension; k++)
            entry.removeAll(Arrays.asList(this.cell[i][k]));
        return entry;
    }


}

class sodukuMultiple{
    public static void main(String[] args) {
        sudoku s1 = new sudoku(6);
        recursion(s1);

    }

    private static void recursion(sudoku s){
        List lis = leastPossible(s);
        //System.out.println(lis);
        for (int i = 0; i < ((List) lis.get(2)).size(); i++) {
            s.set((int) lis.get(0),(int) lis.get(1),(int) ((List) lis.get(2)).get(i));
            if(s.isComplete()){
              printBoard(s);
            }else {
                recursion(s);
            }
            s.set((int) lis.get(0),(int) lis.get(1),-1);
        }
    }

    public static void printBoard(sudoku s1){
        for (int i = 0; i < s1.dimension; i++) {
            for (int j = 0; j < s1.dimension; j++) {
                System.out.print(s1.get(i,j)+" ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public static List leastPossible(sudoku s){
        int min = s.dimension;
        int min1;
        int x=s.dimension;
        int y=s.dimension;
        for (int i = 0; i < s.dimension; i++) {
            for (int j = 0; j < s.dimension; j++) {
                if(s.get(i,j)==-1){
                    min1 = min(min,s.probablenum(i,j).size());
                    if (min1<min){
                        min = min1;
                        x = i;
                        y = j;
                    }
                }}
        }

        //System.out.println("x="+x+"y="+y+"value="+value);
        //s.set(x,y, value);
        return Arrays.asList(x,y,s.probablenum(x,y));
    }
}