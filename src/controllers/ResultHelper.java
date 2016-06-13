package controllers;

/**
 * Created by Michal on 2016-06-12.
 */
public class ResultHelper implements Comparable<ResultHelper> {

    private String provider;
    private double rating;

    public ResultHelper(String p, double r){
        this.provider = p;
        this.rating = r;
    }

    public String getProvider(){
        return provider;
    }


    public double getRating(){
        return rating;
    }

    @Override
    public int compareTo(ResultHelper o) {
        return Double.compare(this.rating,o.getRating());
    }
}
