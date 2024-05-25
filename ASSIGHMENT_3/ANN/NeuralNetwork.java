package ASSIGHMENT_3.ANN;

import java.util.Random;

public class NeuralNetwork {
    private double[] inputLayer;
    private double[] hiddenLayer;
    private double[] outputLayer;
    private double[] weightsInputToHidden;
    private double[] weightsHiddenToOutput;
    private double[] biasesHidden;
    private double[] biasesOutput;

    //learning rate
    public double learningRate= 0.01;
    //stopping condition:
    public double targetError = 0.01;

    private Random random= new Random(45); //seed val;

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes){
        biasesHidden= new double[hiddenNodes];
        biasesOutput= new double[outputNodes];
        inputLayer= new double[inputNodes];
        hiddenLayer= new double[hiddenNodes];
        outputLayer= new double[outputNodes];
        weightsInputToHidden= new double[inputNodes*hiddenNodes];
        weightsHiddenToOutput= new double[hiddenNodes*outputNodes];
        initialiseWeight();
    }

    public void initialiseWeight(){
        for (int i = 0; i < weightsInputToHidden.length; i++) {
            weightsInputToHidden[i] = random.nextDouble() - 0.5;
        }
        for (int i = 0; i < weightsHiddenToOutput.length; i++) {
            weightsHiddenToOutput[i] = random.nextDouble() - 0.5;
        }
        for (int i = 0; i < biasesHidden.length; i++) {
            biasesHidden[i] = random.nextDouble() - 0.5;
        }
        for (int i = 0; i < biasesOutput.length; i++) {
            biasesOutput[i] = random.nextDouble() - 0.5;
        }
    }

    // used sigmoid because it supports binary
    private double sigmoid(double x){
        return 1/(1+ Math.exp(-x));
    }

    private double sigmoidDerivative(double x){
        return x*(1-x);
    }

    public void train(double[] inputs, double[] targets) {
        // Forward pass
        for (int i = 0; i < inputLayer.length; i++) {
            inputLayer[i] = inputs[i];
        }

        //feeding to hidden layer
        int hiddenLength=hiddenLayer.length;
        for(int i=0; i<hiddenLength; i++){
            hiddenLayer[i] = 0;
            for (int j = 0; j < inputLayer.length; j++) {
                hiddenLayer[i] += inputLayer[j] * weightsInputToHidden[j * hiddenLength+ i];
            }
            //activation
            hiddenLayer[i] = sigmoid(hiddenLayer[i]);
        }

        //feeding to output layer
        int outputLength= outputLayer.length;
        for (int i = 0; i < outputLength; i++) {
            outputLayer[i] = 0;
            for (int j = 0; j < hiddenLayer.length; j++) {
                outputLayer[i] += hiddenLayer[j] * weightsHiddenToOutput[j * outputLength + i];
            }
            //activation
            outputLayer[i] = sigmoid(outputLayer[i]);
        }

        //calculate error information
        double[] outputErrors = new double[outputLength];
        for (int i = 0; i < outputLength; i++) {
            outputErrors[i] = targets[i] - outputLayer[i];
        }

        //calculate weight correction
        double[] weightCorrectionsOutput = new double[weightsHiddenToOutput.length];
        for (int i = 0; i < weightsHiddenToOutput.length; i++) {
            int hiddenIndex = i / outputLength;
            int outputIndex = i % outputLength;
            weightCorrectionsOutput[i] = learningRate * outputErrors[outputIndex] * sigmoidDerivative(outputLayer[outputIndex]) * hiddenLayer[hiddenIndex];
        }

        //bias correction
        double[] biasCorrectionsOutput = new double[outputLength];
        for (int i = 0; i < outputLength; i++) {
            biasCorrectionsOutput[i] = learningRate * outputErrors[i] * sigmoidDerivative(outputLayer[i]);
        }

        //sum of delta inputs
        double[] deltaInputsHidden = new double[hiddenLayer.length];
        for (int i = 0; i < hiddenLayer.length; i++) {
            deltaInputsHidden[i] = 0;
            for (int j = 0; j < outputLength; j++) {
                deltaInputsHidden[i] += outputErrors[j] * weightsHiddenToOutput[i * outputLength + j];
            }
        }

         // Calculate the error information term for each node in the hidden layer
        double[] hiddenErrors = new double[hiddenLayer.length];
        for (int i = 0; i < hiddenLayer.length; i++) {
            hiddenErrors[i] = deltaInputsHidden[i] * sigmoidDerivative(hiddenLayer[i]);
        }

        // Calculate the weight correction term for each node in the hidden layer
        double[] weightCorrectionsHidden = new double[weightsInputToHidden.length];
        for (int i = 0; i < weightsInputToHidden.length; i++) {
            int inputIndex = i / hiddenLayer.length;
            int hiddenIndex = i % hiddenLayer.length;
            weightCorrectionsHidden[i] = learningRate * hiddenErrors[hiddenIndex] * sigmoidDerivative(hiddenLayer[hiddenIndex]) * inputLayer[inputIndex];
        }

        // Calculate the bias correction term for each node in the hidden layer
        double[] biasCorrectionsHidden = new double[hiddenLayer.length];
        for (int i = 0; i < hiddenLayer.length; i++) {
            biasCorrectionsHidden[i] = learningRate * hiddenErrors[i] * sigmoidDerivative(hiddenLayer[i]);
        }

        // Update weights 
        for (int i = 0; i < weightsHiddenToOutput.length; i++) {
            weightsHiddenToOutput[i] += weightCorrectionsOutput[i];
        }
        for (int i = 0; i < weightsInputToHidden.length; i++) {
            weightsInputToHidden[i] += weightCorrectionsHidden[i];
        }

        // Update biases
        for (int i = 0; i < outputLength; i++) {
            biasesOutput[i] += biasCorrectionsOutput[i];
        }
        for (int i = 0; i < hiddenLayer.length; i++) {
            biasesHidden[i] += biasCorrectionsHidden[i];
        }
    }

    public double[] predict(double[] inputs) {
        // Feedforward
        for (int i = 0; i < inputLayer.length; i++) {
            inputLayer[i] = inputs[i];
        }
        for (int i = 0; i < hiddenLayer.length; i++) {
            hiddenLayer[i] = 0;
            for (int j = 0; j < inputLayer.length; j++) {
                hiddenLayer[i] += inputLayer[j] * weightsInputToHidden[j * hiddenLayer.length + i];
            }
            hiddenLayer[i] = sigmoid(hiddenLayer[i]);
        }
        for (int i = 0; i < outputLayer.length; i++) {
            outputLayer[i] = 0;
            for (int j = 0; j < hiddenLayer.length; j++) {
                outputLayer[i] += hiddenLayer[j] * weightsHiddenToOutput[j * outputLayer.length + i];
            }
            outputLayer[i] = sigmoid(outputLayer[i]);
        }
        return outputLayer;
    }

    public void fit(double[][] inputDataset, double[][] targetDataset, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            double totalError = 0;
            for (int i = 0; i < inputDataset.length; i++) {
                train(inputDataset[i], targetDataset[i]);
                double[] prediction = predict(inputDataset[i]);
                for (int j = 0; j < prediction.length; j++) {
                    totalError += Math.pow(targetDataset[i][j] - prediction[j], 2);
                }
            }
            totalError /= inputDataset.length;
            System.out.println("Epoch " + (epoch + 1) + ", Error: " + totalError);
            if (totalError < targetError) {
                System.out.println("Training stopped early at epoch " + (epoch + 1) + " due to reaching target error.");
                break;
            }
        }
    }
}
