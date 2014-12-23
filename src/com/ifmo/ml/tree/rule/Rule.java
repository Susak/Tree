package com.ifmo.ml.tree.rule;

import com.ifmo.ml.tree.ClassElements;

import java.io.Serializable;

/**
 * Created by ruslan on 12/15/14.
 */
public class Rule implements Serializable {
    private double separator;
    private int attrId;

    public Rule(double separator, int attrId) {
        this.separator = separator;
        this.attrId = attrId;
    }

    public boolean getClass(ClassElements e) {
        return separator >= e.getAttribute(attrId);
    }

    public double getSeparator() {
        return separator;
    }

    public int getAttrId() {
        return attrId;
    }
}
