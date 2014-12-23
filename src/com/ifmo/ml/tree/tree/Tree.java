package com.ifmo.ml.tree.tree;

import com.ifmo.ml.tree.rule.Rule;

import java.io.Serializable;

/**
 * Created by ruslan on 12/15/14.
 */
public class Tree implements Serializable {
    private Tree left;
    private Tree right;
    private Rule rule;
    private boolean isLeaf;
    private int classElement;

    public Tree(Tree left, Tree right, Rule rule, boolean isLeaf, int classElement) {
        this.left = left;
        this.right = right;
        this.rule = rule;
        this.isLeaf = isLeaf;
        this.classElement = classElement;
    }

    public Tree(boolean isLeaf, int classElement) {
        this.isLeaf = isLeaf;
        this.classElement = classElement;
        this.left = null;
        this.right = null;
        this.rule = null;
    }

    public Tree(Rule rule, Tree left, Tree right) {
        this.rule = rule;
        this.right = right;
        this.left = left;
        this.isLeaf = false;
        this.classElement = Integer.MAX_VALUE;
    }

    public Tree(Tree t) {
        deepCopy(t);
    }

    public Tree getLeft() {
        return left;
    }

    public Tree getRight() {
        return right;
    }

    public Rule getRule() {
        return rule;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public int getClassElement() {
        return classElement;
    }

    private void deepCopy(Tree t) {
        if (t.isLeaf) {
            this.right = null;
            this.left = null;
            this.rule = null;
            this.classElement = t.getClassElement();
            this.isLeaf = true;
        } else {
            this.rule = new Rule(t.getRule().getSeparator(), t.getRule().getAttrId());
            this.classElement = t.getClassElement();
            this.isLeaf = false;
            this.left = new Tree(t.getLeft());
            this.right = new Tree(t.getRight());
        }
    }

    public void setLeft(Tree left) {
        this.left = left;
    }

    public void setRight(Tree right) {
        this.right = right;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public void setClassElement(int classElement) {
        this.classElement = classElement;
    }

    public void set(int e) {
        this.right = null;
        this.left = null;
        this.rule = null;
        this.classElement = e;
        this.isLeaf = true;
    }

    public void set(Tree t) {
        this.right = t.getRight();
        this.left = t.getLeft();
        this.rule = t.getRule();
        this.classElement = t.getClassElement();
        this.isLeaf = t.isLeaf();
    }
}
