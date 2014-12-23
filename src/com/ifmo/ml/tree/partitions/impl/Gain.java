package com.ifmo.ml.tree.partitions.impl;


import com.ifmo.ml.tree.ClassElements;
import com.ifmo.ml.tree.partitions.PartitionFunction;
import com.ifmo.ml.tree.rule.Rule;
import com.ifmo.ml.tree.util.Entropy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ruslan on 12/15/14.
 */
public class Gain implements PartitionFunction {

    @Override
    public Rule partition(List<ClassElements> elements) {
        double all = getInfo(elements);
        int attrId = elements.iterator().next().getAttributes().size();
        double max = 0.;
        int maxAttr = 0;
        for (int i = 0; i < attrId; i++) {
            double curMax = getInfoX(elements, i, all);
            if (Double.compare(curMax, max) > 0) {
                max = curMax;
                maxAttr = i;
            }
        }
        return new Rule(max, maxAttr);
    }

    private double getInfo(List<ClassElements> elements) {
        double c = elements.size();
        long c1 = elements.stream().filter((elem) -> elem.getClassValue() == 1).count();
        long c2 = (long) c - c1;
        return Entropy.getEntropy(Arrays.asList(c1 / c, c2 / c));
    }

    private double getInfoX(List<ClassElements> elements, int attrId, double all) {
        List<Integer> x = new ArrayList<>(elements.stream().map((elem) -> elem.getAttribute(attrId)).collect(Collectors.toSet()));
        double max = 0.;
        double maxMed = 0.;
        Collections.sort(x);
        for (int i = 0; i < x.size() - 1; i++) {
            double med = (x.get(i) + x.get(i + 1)) / 2.;
            List<ClassElements> e1 = elements.stream().
                    filter((elem) -> Double.compare(elem.getAttribute(attrId), med) <= 0)
                    .collect(Collectors.toList());
            List<ClassElements> e2 = elements.stream().
                    filter((elem) -> Double.compare(elem.getAttribute(attrId), med) > 0)
                    .collect(Collectors.toList());
            double c1 =  getInfo(e1) * e1.size() / elements.size();
            double c2 =  getInfo(e2) * e2.size() / elements.size();
            double cur = all - (c1 + c2);
            if (Double.compare(cur, max) > 0) {
                max = cur;
                maxMed = med;
            }
        }
        return maxMed;
    }
}
