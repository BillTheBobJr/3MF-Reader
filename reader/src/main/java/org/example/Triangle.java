package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.PriorityQueue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Triangle {
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;

    private String name;

    private Vertex normal;

    public Triangle(Vertex v1, Vertex v2, Vertex v3, String name){
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;

        this.name = name;

        normal = Vertex.crossProduct(Vertex.crossProduct(v1, v2), Vertex.crossProduct(v1, v3));

    }

    public float getXMax(){
        float x = Math.max(v1.getX(), v2.getX());
        return Math.max(v3.getX(), x);
    }

    public float getYMax(){
        float y = Math.max(v1.getY(), v2.getY());
        return Math.max(v3.getY(), y);
    }

    public float getZMax(){
        float z = Math.max(v1.getZ(), v2.getZ());
        return Math.max(v3.getZ(), z);
    }

    public float getXMin(){
        float x = Math.min(v1.getX(), v2.getX());
        return Math.min(v3.getX(), x);
    }

    public float getYMin(){
        float y = Math.max(v1.getY(), v2.getY());
        return Math.max(v3.getY(), y);
    }

    public float getZMin(){
        float z = Math.min(v1.getZ(), v2.getZ());
        return Math.min(v3.getZ(), z);
    }

    public Vertex[] intersectX(float x){
        ArrayList<Vertex> points = new ArrayList<>();

        Vertex temp = (new Line(v1, v2)).getByX(x);
        if(temp != null) points.add(temp);

        temp = (new Line(v2, v3)).getByX(x);
        if(temp != null) points.add(temp);

        temp = (new Line(v1, v3)).getByX(x);
        if(temp != null) points.add(temp);

        return points.toArray(new Vertex[0]);
    }

    public Vertex[] intersectY(float y){
        ArrayList<Vertex> points = new ArrayList<>();

        Vertex temp = (new Line(v1, v2)).getByY(y);
        if(temp != null) points.add(temp);

        temp = (new Line(v2, v3)).getByY(y);
        if(temp != null) points.add(temp);

        temp = (new Line(v1, v3)).getByY(y);
        if(temp != null) points.add(temp);

        return points.toArray(new Vertex[0]);
    }

    public Vertex[] intersectZ(float z){
        ArrayList<Vertex> points = new ArrayList<>();

        Vertex temp = (new Line(v1, v2)).getByZ(z);
        if(temp != null) points.add(temp);

        temp = (new Line(v2, v3)).getByZ(z);
        if(temp != null) points.add(temp);

        temp = (new Line(v1, v3)).getByZ(z);
        if(temp != null) points.add(temp);

        return points.toArray(new Vertex[points.size()]);
    }

    public Vertex[] intersectLine(Line l){
        ArrayList<Vertex> intersectionPoints = new ArrayList<>();
        Vertex v = (new Line(v1, v2)).intersect(l);
        if(v != null){
            intersectionPoints.add(v);
        }
        v = (new Line(v1, v3)).intersect(l);
        if(v != null){
            intersectionPoints.add(v);
        }
        v = (new Line(v3, v2)).intersect(l);
        if(v != null) {
            intersectionPoints.add(v);
        }
        return intersectionPoints.toArray(new Vertex[intersectionPoints.size()]);
    }


    public boolean checkIntersection(Triangle t2){
        float const1 = Vertex.dotProduct(this.getNormal(), this.getV1());
        float const2 = Vertex.dotProduct(t2.getNormal(), t2.getV1());
        Vertex vector = Vertex.crossProduct(this.normal, t2.normal);
        Line pInter = null;
        if(this.getNormal().getX() != 0 && t2.getNormal().getX() != 0){
            float val = t2.getNormal().getY()/this.getNormal().getY();
            float z = (-val * const1 - t2.getNormal().getX() + const2)/(t2.getNormal().getZ() - val);
            float y = -(this.getNormal().getZ() * z - const1)/(this.getNormal().getY());
            pInter = new Line(vector, new Vertex(0, y, z, "point"), 0);
        }
        if(pInter != null) {
            Vertex[] v1 = this.intersectLine(pInter);
            Vertex[] v2 = t2.intersectLine(pInter);
            if(v1.length + v2.length <= 2) return false;
            PriorityQueue<Float[]> minHeap = new PriorityQueue<>();
            for(Vertex v: v1)minHeap.add(new Float[]{v.getX(), 1F});
            for(Vertex v: v2)minHeap.add(new Float[]{v.getX(), 2F});
            float flag = -1F;
            int change = -1;
            for(Float f[]: minHeap){
                if(!f[1].equals(Float.valueOf(flag))){
                    change++;
                    flag = f[1].floatValue();
                }
            }
            if(change == 1) return true;
        }

        return false;
    }

}
