package com.ifmo.ml.tree.util;

import com.ifmo.ml.tree.ClassElements;
import com.ifmo.ml.tree.tree.Tree;
import com.ifmo.ml.tree.Сlassifier;

import java.util.List;
import java.util.Set;

/**
 * Created by ruslan on 12/17/14.
 */
public class Measure {

    public static double findMeasure(Tree t, List<ClassElements> valid, double b) {
        int[][] tm = new int[2][2];
        for (ClassElements p : valid) {
            int c1 = Сlassifier.getClass(t, p);
            int resClass = (c1 == -1) ? 0 : 1;
            int classValue = (p.getClassValue() == -1) ? 0 : 1;

            tm[resClass][classValue]++;
        }

        if (tm[1][1] == 0) {
            System.out.println("NAN");
            return 0;
        }

        double precision = 1.0 * tm[1][1] / (tm[1][1] + tm[1][0]),
                recall    = 1.0 * tm[1][1] / (tm[1][1] + tm[0][1]);

        /*if (Double.compare(precision, recall) < 0) {
            b = 2.0;
        }*/

        return (1 + b * b) * precision * recall / (b * b * precision + recall);
    }

    public static int getWrong(Tree t, List<ClassElements> test) {
        int counter = 0;
        for (ClassElements p : test) {
            if (p.getClassValue() != Сlassifier.getClass(t, p)) counter++;
        }
        return counter;
    }

    public static boolean getWrong(Tree t, List<ClassElements> test, int control) {
        int counter = 0;
        int pos = 0;
        int neg = 0;
        for (ClassElements p : test) {
            if (p.getClassValue() != Сlassifier.getClass(t, p)) counter++;
            if (p.getClassValue() == Сlassifier.getClass(t, p)) {
                if (p.getClassValue() == 1) pos++;
                else neg++;
            }
        }
        return counter < control && neg != 0 && pos != 0;
    }
}
