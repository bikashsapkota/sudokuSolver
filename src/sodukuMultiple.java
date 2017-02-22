import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static java.lang.StrictMath.min;

/**
 * Created by bikash on 9/5/16.
 */
class sudoku {
    int dimension;
    /*private int[][] cell = {
            {0,0,0,3,0,5},
            {0,3,0,0,0,2},
            {1,2,0,0,4,3},
            {6,0,0,0,0,4},
            {2,1,0,4,0,6},
            {3,4,5,0,0,0}
    };*/
    /*
    private int[][] cell = {
            {5,3,0,0,7,0,0,0,0},
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},
            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
    };*/
    private int[][] cell;

    sudoku(int[][] data){
        this.cell = data;
        dimension=this.cell.length;
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
                if(this.cell[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }



    List probablenum(int i, int j){
        //List<Integer> entry=new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        List<Integer> entry=new ArrayList<Integer>();
        for (int k = 0; k < dimension; k++) {
            entry.add(k+1);
        }

        for (int k=0; k<this.dimension; k++)
            entry.removeAll(Arrays.asList(this.cell[k][j]));

        for (int k=0; k<this.dimension; k++)
            entry.removeAll(Arrays.asList(this.cell[i][k]));

        int temp1 = i/3*3;
        int temp2 = j/3*3;
        //System.out.println(temp1+":"+temp2);
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                entry.removeAll(Arrays.asList(this.cell[temp1+k][temp2+l]));
            }
        }
        return entry;
    }
}

class sodukuMultiple{
     /*public sodukuMultiple(int[][] data){
        sudoku s1 = new sudoku(data);
        //printBoard(s1);
        sudoku s2 =recursion(s1);
        printBoard(s2);
         //return s2;
    }*/
        public static sudoku temp;

    public static sudoku recursion(sudoku s){
        List lis = leastPossible(s);
        //System.out.println(lis);
        for (int i = 0; i < ((List) lis.get(2)).size(); i++) {
            s.set((int) lis.get(0),(int) lis.get(1),(int) ((List) lis.get(2)).get(i));
            if(s.isComplete()){
                printBoard(s);
                temp  = s;
                printBoard(s);
                return s;
            }else {
                return recursion(s);
            }
            //s.set((int) lis.get(0),(int) lis.get(1),0);
        }
        return temp;
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
                if(s.get(i,j)==0){
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