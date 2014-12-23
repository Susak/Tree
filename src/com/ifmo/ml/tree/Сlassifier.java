package com.ifmo.ml.tree;

import com.ifmo.ml.tree.tree.Tree;

/**
 * Created by ruslan on 12/15/14.
 */
public class Сlassifier {

    public static int getClass(Tree t, ClassElements c) {
        if (t.isLeaf()) {
            return t.getClassElement();
        }
        if (t.getRule().getClass(c)) {
            return getClass(t.getLeft(), c);
        } else {
            return getClass(t.getRight(), c);
        }
    }
}
