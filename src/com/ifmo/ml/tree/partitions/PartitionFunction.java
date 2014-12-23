package com.ifmo.ml.tree.partitions;

import com.ifmo.ml.tree.ClassElements;
import com.ifmo.ml.tree.rule.Rule;

import java.util.List;
import java.util.Set;

/**
 * Created by ruslan on 12/15/14.
 */
public interface PartitionFunction {

    Rule partition(List<ClassElements> elements);
}
