package com.ifmo.ml.tree.util;

import java.util.List;

/**
 * Created by ruslan on 12/15/14.
 */
public class Entropy {

    public static double getEntropy(List<Double> values) {
        double res = 0.;
        for (double val : values) {
            res += val * Math.log(val) / Math.log(2.);
        }
        return Double.isNaN(res) ? 0 : -res;
    }
}
