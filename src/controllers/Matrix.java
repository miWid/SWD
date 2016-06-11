package controllers;

import java.util.List;

/**
 * Created by qzmp on 11.06.2016.
 */
public class Matrix {
    private final static double[] RI = {0, 0, 0.58, 0.9, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49};

    private double[][] data;

    private double countRowSum(int rowIndex) {
        double rowSum = 0;
        for(double value : data[rowIndex]) {
            rowSum += value;
        }
        return rowSum;
    }

    private double countColumnSum(int columnIndex) {
        double columnSum = 0;
        for(int rowIndex = 0; rowIndex < data.length; rowIndex++) {
            columnSum += data[rowIndex][columnIndex];
        }
        return columnSum;
    }

    private int countRowNumber(int valueCount) {
        int rowNumber = 0;
        int acc = 0;
        while(valueCount > 0) {
            rowNumber++;
            valueCount -= acc++;
        }
        return rowNumber + 1;
    }

    public Matrix(double[] values){
        /*
        1-0
        2-1
        3-3
        4-6
        5-10
        y=(x^2-x) / 2
         */
        int matrixSize = countRowNumber(values.length);
        data = new double[matrixSize][matrixSize];
        int processedValues = 0;

        for(int rowIndex = 0; rowIndex < data.length - 1; rowIndex++) {
            for(int columnIndex = rowIndex + 1; columnIndex < data.length; columnIndex++) {
                data[rowIndex][columnIndex] = values[processedValues++];
            }
        }

        processedValues = 0;
        for(int columnIndex = 0; columnIndex < data.length; columnIndex++) {
            for(int rowIndex = 1; rowIndex < data.length; rowIndex++) {
                data[rowIndex][columnIndex] = 1 / values[processedValues];
            }
        }

        for(int i = 0; i < data.length; i++) {
            data[i][i] = 1;
        }
    }

    public void normalize() {
        double columnSum;
        for(int columnIndex = 0; columnIndex < data.length; columnIndex++) {
            columnSum = countColumnSum(columnIndex);

            for(int rowIndex = 0; rowIndex < data.length; rowIndex++) {
                data[rowIndex][columnIndex] /= columnSum;
            }
        }
    }

    public double[] getPreferencesVector() {
        double[] preferencesVector = new double[3];
        double rowSum;
        for(int rowIndex = 0; rowIndex < data.length; rowIndex++) {
            rowSum = countRowSum(rowIndex);

            preferencesVector[rowIndex] = rowSum / data.length;
        }
        return preferencesVector;
    }

    public boolean isConsistent(){
        return isConsistent(0.1);
    }

    public boolean isConsistent(double acceptedRatio) {
        double CI = 0;
        for(int rowIndex = 0; rowIndex < data.length; rowIndex++) {
            for(int columnIndex = 0; columnIndex < data[rowIndex].length; columnIndex++) {
                CI += countRowSum(rowIndex) * countColumnSum(columnIndex);
            }
        }
        CI -= data.length;
        CI /= data.length - 1;

        double CR = CI/RI[data.length];
        return CR < acceptedRatio;
    }

}