package com.ifmo.ml.tree.partitions.impl;

import com.ifmo.ml.tree.ClassElements;
import com.ifmo.ml.tree.partitions.PartitionFunction;
import com.ifmo.ml.tree.rule.Rule;
import com.ifmo.ml.tree.util.Entropy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by ruslan on 12/22/14.
 */
public class GiniGain implements PartitionFunction {
    private static final int[] randomValues = {5, 10, 15, 25, 50, 18, 7};
    private static Random r = new Random();
    @Override
    public Rule partition(List<ClassElements> elements) {
        int attrId = elements.iterator().next().getAttributes().size();
        double max = 0.;
        int maxAttr = 0;
        double medMax = 0;
        for (int i = 0; i < attrId; i++) {
            List<Double> attrs = getAttrList(elements, i);
            for (int j = 0; j < attrs.size(); j++) {
                if (j % 5 != 0) {
                    continue;
                }
                double med = attrs.get(j) + getInfoX();
//            double med = getInfoX(elements, i, all);
                final int attr = i;
                List<ClassElements> e1 = elements.stream().
                        filter((elem) -> Double.compare(elem.getAttribute(attr), med) <= 0)
                        .collect(Collectors.toList());
                List<ClassElements> e2 = elements.stream().
                        filter((elem) -> Double.compare(elem.getAttribute(attr), med) > 0)
                        .collect(Collectors.toList());
                double c1 = getGini(e1) * e1.size() / elements.size();
                double c2 = getGini(e2) * e2.size() / elements.size();
                double cur = (c1 + c2);
                if (Double.isNaN(cur)) {
                    continue;
                }
                if (Double.compare(cur, max) < 0) {
                    max = cur;
                    maxAttr = i;
                    medMax = med;
                }
            }
        }
        return new Rule(medMax, maxAttr);
    }

    private double getGini(List<ClassElements> elements) {
        double c = elements.size();
        long c1 = elements.stream().filter((elem) -> elem.getClassValue() == 1).count();
        long c2 = (long) c - c1;
        return 1. - c1 / c + c2 / c ;
    }

    private double getInfoX() {
        return randomValues[Math.abs(r.nextInt()) % randomValues.length];
    }

    private List<Double> getAttrList(List<ClassElements> elements, int id) {
        return elements.stream().map((e) -> (double) e.getAttribute(id)).sorted().collect(Collectors.toList());
    }

    private List<Double> getRandomAttr() {
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            res.add((double) r.nextInt(100));
        }
        for (int i = 0; i < 10; i++) {
            res.add((double) r.nextInt(800) + 100);
        }
        return res;
    }

}
