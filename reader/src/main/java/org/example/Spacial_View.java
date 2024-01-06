package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Spacial_View {

    private Spacial_Context object;

    private float xMax;
    private float xMin;
    private float yMax;
    private float yMin;
    private float zMax;
    private float zMin;
    private boolean isEmpty;


    public Spacial_View(){
        object = new Voxel();
        isEmpty = true;
    }

    public void insert(Triangle t) {
        if(isEmpty){
            xMax = t.getXMax();
            yMax = t.getYMax();
            zMax = t.getZMax();
            xMin = t.getXMin();
            yMin = t.getYMin();
            zMin = t.getZMin();
            isEmpty = false;
        } else {
            if(xMax < t.getXMax()) xMax = t.getXMax();
            if(yMax < t.getYMax()) yMax = t.getYMax();
            if(zMax < t.getZMax()) zMax = t.getZMax();
            if(xMin > t.getXMin()) xMin = t.getXMin();
            if(yMin > t.getYMin()) yMin = t.getYMin();
            if(zMin > t.getZMin()) zMin = t.getZMin();
        }
        object.insert(t);
    }

    public void complete(){
        object.setXMax(this.xMax);
        object.setXMin(this.xMin);
        object.setYMax(this.yMax);
        object.setYMin(this.yMin);
        object.setZMax(this.zMax);
        object.setZMin(this.zMin);
        Spacial_Context vs = object.split();
        if(vs != null){
            object = vs;
            object.split();
        }
        for(String[] s: object.getCount()){
            System.out.println(Arrays.toString(s));
        }
    }





    public ArrayList<Triangle[]> findIntersectingTriangles(){
        return object.findIntersection();
    }
}
