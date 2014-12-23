package com.ifmo.ml.tree;

import jdk.internal.util.xml.impl.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by ruslan on 12/15/14.
 */
public class DataParser {
    public static List<ClassElements> parse(InputStream attrs, InputStream vals) {
        List<ClassElements> result = new ArrayList<>();
        try (BufferedReader attr = new BufferedReader(new InputStreamReader(attrs));
             BufferedReader val = new BufferedReader(new InputStreamReader(vals))) {
            String attrS, valS;
            while ((attrS = attr.readLine()) != null && (valS = val.readLine()) != null) {
                result.add(new ClassElements(getAttrs(attrS), Integer.parseInt(valS)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static List<Integer> getAttrs(String st) {
        String[] array = st.split(" ");
        List<Integer> res = new ArrayList<>();
        for (String s : array) {
            res.add(Integer.parseInt(s));
        }
        return res;
    }

}
