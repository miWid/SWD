package controllers;

import java.util.List;

/**
 * Created by Michal on 2016-06-10.
 */
public interface Ahp {

    public Double[][] createPreferencesMatrix(List<Double> values);
    public Double[][] normalizeMatrix(Double[][] matrix);
    public Double[] countPreferencesVector(Double[][] normalizedMatrix);
    public boolean isConsistent(Double[][] matrix);
    public Double[] createRanking(List<Double[]> vectors);
    public String getBestDecision(Double[] ranking);

}
