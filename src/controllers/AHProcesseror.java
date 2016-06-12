package controllers;

import java.util.List;

/**
 * Created by qzmp on 11.06.2016.
 */
public class AHProcesseror {

    private CriteriaNode[] criteria;

    private class Node {
        protected double value;

        public void setValue(double value) {
            this.value = value;
        }
    }

    private class CriteriaNode extends Node{
        private Node[] children;

        public void createEndPreferences(double[] preferences) {
            children = new Node[preferences.length];
            for(int i = 0; i < preferences.length; i++) {
                children[i] = new Node();
                children[i].setValue(preferences[i] * this.value);
            }
        }

        public void addSubcriteriaPreferences(double[] preferences) {
            children = new CriteriaNode[preferences.length];
            for(int i = 0; i < preferences.length; i++) {
                children[i] = new CriteriaNode();
                children[i].setValue(preferences[i] * this.value);
            }
        }

        public Node getChild(int index) {
            return children[index];
        }

        public int getFinalChildrenNumber() {
            return children[0].getClass().equals(this.getClass()) ? ((CriteriaNode)children[0]).getFinalChildrenNumber() : children.length;
        }

        public double getFinalChildValue(int index) {
            double rating = 0;
            if(children[0].getClass().equals(this.getClass())) {
                for(Node child : children) {
                    rating += ((CriteriaNode) child).getFinalChildValue(index);
                }
            }
            else {
                return children[index].value;
            }
            return rating;
        }
    }

    public void addCriteriaPreferences(double[] criteriaPreferences) {
        criteria = new CriteriaNode[criteriaPreferences.length];
        for(int i = 0; i < criteria.length; i++) {
            criteria[i] = new CriteriaNode();
            criteria[i].setValue(criteriaPreferences[i]);
        }
    }

    public void addSubcriteriaPreferences(int criteriaIndex, double[] preferences) {
        criteria[criteriaIndex].addSubcriteriaPreferences(preferences);
    }

    public void addFinalPreferences(int criterionIndex, int subcriterionIndex, double[] preferences) {
        ((CriteriaNode)criteria[criterionIndex].getChild(subcriterionIndex)).createEndPreferences(preferences);
    }

    public void addFinalPreferences(int criterionIndex, double[] preferences) {
        criteria[criterionIndex].createEndPreferences(preferences);
    }

    private int getIndexOfMax(double[] arr) {
        double max = arr[0];
        int maxIndex = 0;

        for(int i = 1; i < arr.length; i++) {
            if(arr[i] > max) {
                max = arr[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public double[] getRatings() {
        int choiceSize = criteria[0].getFinalChildrenNumber();
        double[] ratings = new double[choiceSize];
        for(int i = 0 ; i < choiceSize; i++) {
            for(CriteriaNode criterion : criteria) {
                ratings[i] += criterion.getFinalChildValue(i);
            }

        }
        return ratings;
    }

    public int getBestDecisionIndex() {
        return getIndexOfMax(getRatings());
    }
}
