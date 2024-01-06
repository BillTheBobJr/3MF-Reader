package org.example;

import lombok.Data;

import java.util.ArrayList;

@Data
public abstract class Spacial_Context {

    public float xMax;
    public float xMin;
    public float yMax;
    public float yMin;
    public float zMax;
    public float zMin;

    public abstract void insert(Triangle t);
    public abstract Spacial_Context split();

    public abstract ArrayList<String[]> getCount();

    public abstract ArrayList<Triangle[]> findIntersection();
}
