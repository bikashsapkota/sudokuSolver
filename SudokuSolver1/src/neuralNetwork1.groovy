/**
 * Created by bikash on 6/26/16.
 */
package Jama
import java.text.SimpleDateFormat

import static java.util.Calendar.instance

class neuralNetwork {
    def inputnodes
    def hiddennodes
    def outputnodes
    def learningrate
    def wih
    def who

    neuralNetwork(def inputnodes,def hiddennodes,def outputnodes,def learningrate){
        this.inputnodes = inputnodes
        this.hiddennodes = hiddennodes
        this. outputnodes = outputnodes
        this.learningrate = learningrate
        this.wih = subtract(Matrix.random(inputnodes,hiddennodes),0.5,1)
        this.who = subtract(Matrix.random(hiddennodes,outputnodes),0.5,1)
    }


    public static void train(Matrix inputs, Matrix targets, neuralNetwork network){
        //input size = 1 * 784
        //network.wih = 784*100
        Matrix hidden_input = inputs.times(network.wih)
        //hidden_input = 1*100
        Matrix hidden_output = network.activation(hidden_input)
        //hidden_output = 1*100
        //network.who = 100*10
        Matrix final_input = hidden_output.times(network.who)
        //final_input = 1*10
        Matrix final_output = network.activation(final_input)
        //final_output = 1*10
        //targets = 1*10
        Matrix output_error = final_output.minus(targets);
        //output_error = 1*10
        //network.who.transpose() = 10*100
        Matrix hidden_error = output_error.times(network.who.transpose())
        //hidden_error = 1*100
        network.who.plusEquals(hidden_output.transpose().times(multiply(multiply(output_error,final_output),subtract(final_output,1.0,-1))).times(network.learningrate))
        network.wih.plusEquals(inputs.transpose().times(multiply(multiply(hidden_error,hidden_output),subtract(hidden_output,1.0,-1))).times(network.learningrate))
        //println(network.who.getArray())
    }

    public static Matrix predict(Matrix input, neuralNetwork network){
        Matrix hidden_input = input.times(network.wih)
        Matrix hidden_output = activation(hidden_input)
        Matrix final_input =  hidden_output.times(network.who)
        Matrix final_output = activation(final_input)
        return final_output

    }

     public static activation(Matrix matrix){
        for (int i = 0; i < matrix.rowDimension; i++) {
            for (int j = 0; j < matrix.columnDimension; j++) {
                matrix.set(i,j, 1/(1+Math.exp(-matrix.get(i,j))))
            }

        }
        return matrix
    }

    public static multiply(Matrix m1 , Matrix m2){
        Matrix matrix=new Matrix(m1.rowDimension,m1.columnDimension)
            if (m1.columnDimension == m2.columnDimension && m1.rowDimension==m2.rowDimension) {
                for (int i = 0; i < m1.rowDimension; i++) {
                    for (int j = 0; j < m1.columnDimension; j++) {
                        matrix.set(i, j, m1.get(i, j) * m2.get(i, j))
                    }
                }
                return matrix
            }else{
                println("Row and column size are not equvalent")
            }
    }

     static subtract(Matrix matrix, double num, int sign=1){
        for (int i = 0; i < matrix.rowDimension; i++) {
            for (int j = 0; j < matrix.columnDimension; j++) {
                matrix.set(i,j,(matrix.get(i,j)-num)*sign)
            }

        }
        return matrix
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in)
        def input_nodes = 784
        def hidden_nodes = 100
        def output_nodes = 10
        def learning_rate = 0.3
        def n1 = new neuralNetwork(input_nodes,hidden_nodes,output_nodes,learning_rate)

        Matrix inputs = new Matrix(input_nodes, 1)



        def trainData = new File('/media/bikash/Study/Python/mnist_dataset/mnist_train.csv').getText().tokenize('\n');
        def testData = new File('/media/bikash/Study/Python/mnist_dataset/mnist_test.csv').getText().tokenize('\n');

        println(new SimpleDateFormat("HH:mm:ss").format(getInstance().getTime()))
        /*trainData.each {
            def list2 = []
            def list = it.tokenize(',')
            for(String s : list.subList(1,785)) list2.add(Integer.valueOf(s)/255*0.99 + 0.01);
            int test = list[0].toInteger();
            Matrix targets = new Matrix(output_nodes, 1)
            targets.set(test,0,(double) 0.99)
            for (int i = 0; i < inputs.rowDimension; i++) {
                inputs.set(i,0,(double) list2[i]);
            }

            train(inputs.transpose(),targets.transpose(),n1);
        }*/

        println(new SimpleDateFormat("HH:mm:ss").format(getInstance().getTime()))

        int correct = 0
        testData.each {
            def list2 = []
            def list = it.tokenize(',')
            for(String s : list.subList(1,785)) list2.add(Integer.valueOf(s)/255*0.99 + 0.01);
            int value = list[0].toInteger();

            for (int i = 0; i < inputs.getRowDimension(); i++) {
                inputs.set(i,0,(double) list2[i]);
            }

             Matrix m =predict(inputs.transpose(),n1);


            int maxIndex = 0;
            for (int i = 0; i < 10; i++){
                int newnumber = m.get(0,i);
                if ((newnumber > m.get(0,maxIndex))){
                    maxIndex = i;
                }
            }
            if (maxIndex==value){
                correct++
            }

        }
        println(correct)

    }

}