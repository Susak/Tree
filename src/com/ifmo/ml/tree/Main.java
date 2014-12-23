package com.ifmo.ml.tree;

import com.ifmo.ml.tree.partitions.impl.RandomGain;
import com.ifmo.ml.tree.tree.Tree;
import com.ifmo.ml.tree.tree.TreeBuilder;
import com.ifmo.ml.tree.util.Measure;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * Created by ruslan on 12/15/14.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream trainAttr = new FileInputStream("/home/ruslan/IdeaProjects/machine-learning/Tree/trees/arcene_train.data");
        InputStream trainLabel = new FileInputStream("/home/ruslan/IdeaProjects/machine-learning/Tree/trees/arcene_train.labels");

        InputStream validAttr = new FileInputStream("/home/ruslan/IdeaProjects/machine-learning/Tree/trees/arcene_valid.data");
        InputStream validLabel = new FileInputStream("/home/ruslan/IdeaProjects/machine-learning/Tree/trees/arcene_valid.labels");

        List<ClassElements> trains = DataParser.parse(trainAttr, trainLabel);
        List<ClassElements> valid = DataParser.parse(validAttr, validLabel);
        System.out.println("End parse data");
        Tree best = null;
        double maxMes = 0.;
        double all = 0.;
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(trains);
            List<ClassElements> elements = trains.subList(0, trains.size() * 2 / 3);
            Tree t = TreeBuilder.build(elements, new RandomGain());

            List<ClassElements> test = trains.subList(trains.size() * 2 / 3, trains.size());
        Tree cut = TreeBuilder.cutTree(t, test);
            double curMes = Measure.findMeasure(cut, valid, 1.);
            if (Double.compare(curMes, maxMes) > 0) {
                maxMes = curMes;
                best = cut;
            }
            all += curMes;
            System.out.println(i);
        }
        System.out.println(Measure.findMeasure(best, valid, 1.));
        System.out.println(all / 10);
        serializeTree(best);
    }

    private static void serializeTree(Tree t) {
        try (FileOutputStream fos = new FileOutputStream("temp.out");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Tree deserializeTree() {
        Tree t = null;
        try (InputStream fos = new FileInputStream("temp.out");
             ObjectInputStream oos = new ObjectInputStream(fos)) {
            t = (Tree) oos.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }
}
