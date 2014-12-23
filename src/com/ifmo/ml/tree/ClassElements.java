package com.ifmo.ml.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ruslan on 12/15/14.
 */
public class ClassElements {
    private List<Integer> attributes = new ArrayList<>(10000);
    private int classValue;
    private List<Boolean> used ;

    public ClassElements(List<Integer> attributes, int classValue) {
        this.attributes = attributes;
        this.classValue = classValue;
        this.used = new ArrayList<>(Collections.nCopies(attributes.size(), false));

    }

    public List<Integer> getAttributes() {
        return attributes;
    }

    public int getClassValue() {
        return classValue;
    }

    public int getAttribute(int id) {
        return attributes.get(id);
    }

    public int getOther() {
        return classValue == -1 ? 1 : -1;
    }
}
