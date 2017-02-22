/**
 * Created by bikash on 7/4/16.
 */
package Jama
class test {
    public static void main(String[] args) {
        Matrix m1 = Matrix.identity(2,2)
        Matrix m2= Matrix.identity(2,2)
        println(m1.getArray())
        println(m1.transpose().times(5).getArray())
        m2.plusEquals(m2)
        //println(m2.plusEquals(m2).getArray())

    }
}
