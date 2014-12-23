package com.ifmo.ml.tree.tree;

import com.ifmo.ml.tree.ClassElements;
import com.ifmo.ml.tree.partitions.PartitionFunction;
import com.ifmo.ml.tree.rule.Rule;
import com.ifmo.ml.tree.util.Measure;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ruslan on 12/15/14.
 */
public class TreeBuilder {
    private static int classSize = 10;

    public static Tree build(List<ClassElements> elements, PartitionFunction function) {
        if (elements.size() == 0) {
            return null;
        }
        //System.out.println(elements.size());
        if (elements.size() < classSize || checkElements(elements)) {
            //System.out.println(elements.size());
            long c1 = elements.stream().filter((elem) -> elem.getClassValue() == -1).count();
            long c2 = elements.size() - c1;
            int c = 1;
            if (c1 > c2) {
                c = -1;
            }
            return new Tree(true, c);
        }
        Rule r = function.partition(elements);
        List<ClassElements> left = elements.stream().filter(r::getClass).collect(Collectors.toList());
        List<ClassElements> right = elements.stream().filter((elem) -> !r.getClass(elem)).collect(Collectors.toList());
        return new Tree(r, build(left, function), build(right, function));
    }

    private static boolean checkElements(List<ClassElements> elements) {
        int val = elements.get(0).getClassValue();
        for (ClassElements e : elements) {
            if (val != e.getClassValue()) {
                return false;
            }
        }
        return true;
    }


    public static boolean compareTree(Tree a, Tree b) {
        if (a.isLeaf() != b.isLeaf()) {
            return false;
        }
        if (a.isLeaf()) {
            return a.getClassElement() == b.getClassElement();
        }
        return compareTree(a.getLeft(), b.getLeft()) && compareTree(a.getRight(), b.getRight());
    }

    public static Tree cutTree(Tree t, List<ClassElements> test) {
        int control = Measure.getWrong(t, test);
        Tree current = new Tree(t);
        while (cutTree(current, test, current, control)) {
            control = Measure.getWrong(current, test);
            System.out.println(control);
        }
        return current;
    }

    private static boolean cutTree(Tree t, List<ClassElements> test, Tree root, int control) {
        if (t.isLeaf()) {
            return false;
        }
        if (t != root) {
            Tree tmp = new Tree(t);
            t.set(-1);
            if (Measure.getWrong(root, test, control)) return true;

//            int l = Measure.getWrong(root, test);
//            if (l < control) {
//                return true;
//            }
            t.set(1);
            if (Measure.getWrong(root, test, control)) return true;
//            int r = Measure.getWrong(root, test);
//            if (r < control) {
//                return true;
//            }
            t.set(tmp);
        }
        return cutTree(t.getLeft(), test, root, control) || cutTree(t.getRight(), test, root, control);
    }
}
