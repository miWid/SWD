package controllers;

import java.util.List;

/**
 * Created by qzmp on 11.06.2016.
 */
public class Matrix {
    private final static double[] RI = {0, 0, 0.58, 0.9, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49};

    private double[][] data;
    private double[] columnSumsBeforeNormalization;

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
        int acc = 1;
        while(valueCount > 0) {
            rowNumber++;
            valueCount -= acc++;
        }
        return rowNumber + 1;
    }

    public Matrix(double[] values){
        setValues(values);
    }

    public void setValues(double[] values) {
        int matrixSize = countRowNumber(values.length);
        data = new double[matrixSize][matrixSize];

        int processedValues = 0;
        for(int rowIndex = 0; rowIndex < data.length - 1; rowIndex++) {
            for(int columnIndex = rowIndex + 1; columnIndex < data.length; columnIndex++) {
                data[rowIndex][columnIndex] = values[processedValues++];
            }
        }

        processedValues = 0;
        for(int columnIndex = 0; columnIndex < data.length - 1; columnIndex++) {
            for(int rowIndex = columnIndex + 1; rowIndex < data.length; rowIndex++) {
                data[rowIndex][columnIndex] = 1 / values[processedValues++];
            }
        }

        for(int i = 0; i < data.length; i++) {
            data[i][i] = 1;
        }
    }

    public void normalize() {
        columnSumsBeforeNormalization = new double[data.length];
        double columnSum;
        for(int columnIndex = 0; columnIndex < data.length; columnIndex++) {
            columnSum = countColumnSum(columnIndex);
            columnSumsBeforeNormalization[columnIndex] = columnSum;

            for(int rowIndex = 0; rowIndex < data.length; rowIndex++) {
                data[rowIndex][columnIndex] /= columnSum;
            }
        }
    }

    public double[] getPreferencesVector() {
        double[] preferencesVector = new double[data.length];
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
        for(int i = 0; i < data.length; i++) {
            CI += countRowSum(i)/data.length * columnSumsBeforeNormalization[i];
        }
        CI -= data.length;
        CI /= data.length - 1;

        double CR = CI/RI[data.length];
        return CR < acceptedRatio;
    }

}
