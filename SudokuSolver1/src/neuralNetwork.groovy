/*Implementation are clear in matlab code*/


import Jama.Matrix

import java.text.SimpleDateFormat

import static java.util.Calendar.instance

public class neuralNetwork {
    int inputnodes
    int hiddennodes
    int outputnodes
    double learningrate
    Matrix wih
    Matrix who
    ArrayList<Float> temp

    neuralNetwork(def inputnodes=784,def hiddennodes=100,def outputnodes=10,def learningrate=0.2){
        this.inputnodes = inputnodes //sets no of inputnodes for the network
        this.hiddennodes = hiddennodes //sets no of hiddennodes for the network
        this.outputnodes = outputnodes //sets no of output for the network
        this.learningrate = learningrate //sets learning rate for network

        this.wih = readcsv("/home/bikash/IdeaProjects/handWritingRecognization/src/wih.csv",new Matrix(this.hiddennodes,this.inputnodes))
        this.who = readcsv("/home/bikash/IdeaProjects/handWritingRecognization/src/who.csv",new Matrix(this.outputnodes,this.hiddennodes))
        //this.wih = subtract(Matrix.random(hiddennodes,inputnodes),0.5) //bias weight between each input nodes and hidden nodes
        //this.who = subtract(Matrix.random(outputnodes,hiddennodes),0.5) //bias weight between each hidden nodes and output nodes
    }


    public static void train(Matrix inputs, Matrix targets, neuralNetwork network){
        Matrix hidden_inputs = network.wih.times(inputs) //hidden_input = weight_input_hidden*inputs
        Matrix hidden_outputs = activation(hidden_inputs)
        Matrix final_inputs = network.who.times(hidden_outputs)
        Matrix final_outputs =  activation(final_inputs)

        Matrix output_errors =  targets.minus(final_outputs)
        Matrix hidden_errors = network.who.transpose().times(output_errors)
        Matrix m1 = multiply(output_errors,final_outputs, subtract(final_outputs,1.0,-1))
        Matrix m2 = multiply(hidden_errors,hidden_outputs, subtract(hidden_outputs,1.0,-1))
        network.who.plusEquals(m1.times(hidden_outputs.transpose()).times(network.learningrate));
        network.wih.plusEquals(m2.times(inputs.transpose()).times(network.learningrate));
    }

    public static int predict(Matrix inputs, neuralNetwork network){
        Matrix hidden_inputs = network.wih.times(inputs)
        Matrix hidden_outputs = activation(hidden_inputs)
        Matrix final_inputs = network.who.times(hidden_outputs)
        Matrix final_outputs =  activation(final_inputs)

        int maxIndex = 0;
        for (int i = 1; i < 10; i++){
            if (final_outputs.get(i,0) > final_outputs.get(maxIndex,0))
                maxIndex = i;
        }
        if(final_outputs.get(maxIndex,0)<0.15)
            return 0
        return maxIndex
    }


    public static activation(Matrix matrix){
        Matrix m  = new Matrix(matrix.rowDimension, matrix.columnDimension)
        for (int i = 0; i < matrix.rowDimension; i++) {
            for (int j = 0; j < matrix.columnDimension; j++) {
                m.set(i,j, 1/(1+Math.exp(-matrix.get(i,j))))
            }

        }
        return m
    }


    public static multiply(Matrix m1 , Matrix m2, Matrix m3){
        Matrix matrix=new Matrix(m1.rowDimension,m1.columnDimension)
        if (m1.columnDimension == m2.columnDimension  && m1.rowDimension==m2.rowDimension && m3.rowDimension==m2.rowDimension && m3.columnDimension == m2.columnDimension) {
            for (int i = 0; i < m1.rowDimension; i++) {
                for (int j = 0; j < m1.columnDimension; j++) {
                    matrix.set(i, j, m1.get(i, j) * m2.get(i, j) * m3.get(i,j))
                }
            }
            return matrix
        }else{
            println("Row and column size are not equvalent")
        }
    }

    static subtract(Matrix matrix, double num, int sign=1){
        Matrix m = new Matrix(matrix.rowDimension,matrix.columnDimension)
        for (int i = 0; i < matrix.rowDimension; i++) {
            for (int j = 0; j < matrix.columnDimension; j++) {
                m.set(i,j,(matrix.get(i,j)-num)*sign)
            }

        }
        return m;
    }


    public static void main(String[] args) {
        def input_nodes = 784
        def hidden_nodes = 100
        def output_nodes = 10
        def learning_rate = 0.3
        def n1 = new neuralNetwork(input_nodes,hidden_nodes,output_nodes,learning_rate)

        //training part
        Matrix inputs = new Matrix(input_nodes, 1)

        def trainData = new File('/media/bikash/Study/Python/mnist_dataset/mnist_train.csv').getText();
        def testData = new File('/media/bikash/Study/Python/mnist_dataset/mnist_test.csv').getText();
        trainData = trainData.tokenize('\n');
        testData = testData.tokenize('\n')
        println("Training Sets: "+trainData.size())

        println("Training Started at: "+new SimpleDateFormat("HH:mm:ss").format(getInstance().getTime()))
        trainData.each {
            Matrix targets = new Matrix(output_nodes, 1)
            for (int i = 0; i < 10; i++)
                targets.set(i,0,0.01);

            def list2 = []
            def list = it.tokenize(',')
            //for(String s : list.subList(1,785)) list2.add(Integer.valueOf(s)/255*0.9999 + 0.0001);
            list.subList(1,785).each {
                list2.add(Integer.valueOf(it)/255*0.9999 + 0.0001)
            }
            int value = list[0].toInteger();

            targets.set(value,0,(double) 0.999999)

            for (int i = 0; i < inputs.getRowDimension(); i++) {
                inputs.set(i,0,(double) list2[i]);
            }
            train(inputs,targets,n1);
        }

        println("Training ended at: "+new SimpleDateFormat("HH:mm:ss").format(getInstance().getTime()))
        println("Testing Sets: "+testData.size())
        int correct = 0
        testData.each {
            def list2 = []
            def list = it.tokenize(',')
            for(String s : list.subList(1,785)) list2.add(Integer.valueOf(s)/255*0.99 + 0.01);
            int value = list[0].toInteger();

            for (int i = 0; i < inputs.getRowDimension(); i++)
                inputs.set(i,0,(double) list2[i]);


            int num =predict(inputs,n1);
            if (num==value)
                correct=correct+1
        }
        println("Accuracy: "+correct/10000*100+"%")
    }

    def readcsv(String path,Matrix mat){
        def data = new File(path).getText()
        def row = 0, col=0
        data.tokenize('\n').each {
            row=0
            it.tokenize(',').each {
                mat.set(col,row,Double.valueOf(it))
                row++
            }
            col++
        }
        return mat
    }
}