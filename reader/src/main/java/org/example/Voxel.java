package org.example;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@NoArgsConstructor
@Data
public class Voxel extends  Spacial_Context{

    int maxTriangles = 300;
    int currTriangles = 0;

    int depth;

    ArrayList<Triangle> voxel = new ArrayList<>();;

    public Voxel(float xMax, float xMin, float yMax, float yMin, float zMax, float zMin){
        this.depth = 0;
        currTriangles = 0;
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;
        this.zMax = zMax;
        this.zMin = zMin;
    }

    public Voxel(float xMax, float xMin, float yMax, float yMin, float zMax, float zMin, int depth){
        this.depth = depth;
        currTriangles = 0;
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;
        this.zMax = zMax;
        this.zMin = zMin;
    }

    public void insert(Triangle t){
        currTriangles++;
        voxel.add(t);
    }

    public Spacial_Context split() {
        if(currTriangles <= maxTriangles || depth == 5) return null;
        VoxelSet vs = new VoxelSet(xMax, xMin, yMax, yMin, zMax, zMin);
        Voxel[] group1 = splitX(this);
        Voxel[] group2 = splitY(group1[1]);
        group1 = splitY(group1[0]);
        Voxel[] group3 = splitZ(group2[0]);
        Voxel[] group4 = splitZ(group2[1]);
        group2 = splitZ(group1[1]);
        group1 = splitZ(group1[0]);

        vs.addVoxel(0, group1[0]);
        vs.addVoxel(1, group1[1]);
        vs.addVoxel(2, group2[0]);
        vs.addVoxel(3, group2[1]);
        vs.addVoxel(4, group3[0]);
        vs.addVoxel(5, group3[1]);
        vs.addVoxel(6, group4[0]);
        vs.addVoxel(7, group4[1]);

        return vs;
    }

    protected Voxel[] splitX(Voxel v){
        float divider = (v.xMax + v.xMin)/2;
        Voxel left = new Voxel(divider, v.xMin, v.yMax, v.yMin, v.zMax, v.zMin, depth + 1);
        Voxel right = new Voxel(v.xMax, divider, v.yMax, v.yMin, v.zMax, v.zMin, depth + 1);
        boolean inLeft = false;
        boolean inRight = false;
        for(Triangle t : v.voxel){
            Vertex back[] = t.intersectZ(v.zMax);
            Vertex front[] = t.intersectZ(v.zMin);
            Vertex top[] = t.intersectY(v.yMax);
            Vertex bottom[] = t.intersectY(v.yMin);
            if(back.length > 0){
                float max = back[0].getX();
                for(int i = 1; i < back.length; i++) max = Math.max(max, back[i].getX());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = back[0].getX();
                for(int i = 1; i < back.length; i++) min = Math.min(min, back[i].getX());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if((!inLeft || !inRight) && front.length > 0){
                float max = front[0].getX();
                for(int i = 1; i < front.length; i++) max = Math.max(max, front[i].getX());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = front[0].getX();
                for(int i = 1; i < front.length; i++) min = Math.min(min, front[i].getX());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if((!inLeft || !inRight) && top.length > 0){
                float max = top[0].getX();
                for(int i = 1; i < top.length; i++) max = Math.max(max, top[i].getX());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = top[0].getX();
                for(int i = 1; i < top.length; i++) min = Math.min(min, top[i].getX());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if((!inLeft || !inRight) && bottom.length > 0){
                float max = bottom[0].getX();
                for(int i = 1; i < bottom.length; i++) max = Math.max(max, bottom[i].getX());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = bottom[0].getX();
                for(int i = 1; i < bottom.length; i++) min = Math.min(min, bottom[i].getX());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if(!inLeft && !inRight){
                if(t.getXMax() >= divider){
                    inRight = true;
                }
                if(t.getXMin() <= divider){
                    inLeft = true;
                }
            }
            if(inLeft)left.insert(t);
            if(inRight)right.insert(t);
            inLeft = false;
            inRight = false;
        }
        return new Voxel[]{left, right};
    }

    protected Voxel[] splitY(Voxel v){
        float divider = (v.yMax + v.yMin)/2;
        Voxel left = new Voxel(v.xMax, v.xMin, divider, v.yMin, v.zMax, v.zMin, depth + 1);
        Voxel right = new Voxel(v.xMax, v.xMin, v.yMax, divider, v.zMax, v.zMin, depth + 1);
        boolean inLeft = false;
        boolean inRight = false;
        for(Triangle t : v.voxel){
            Vertex back[] = t.intersectZ(v.zMax);
            Vertex front[] = t.intersectZ(v.zMin);
            Vertex top[] = t.intersectX(v.xMax);
            Vertex bottom[] = t.intersectX(v.xMin);
            if(back.length > 0){
                float max = back[0].getY();
                for(int i = 1; i < back.length; i++) max = Math.max(max, back[i].getY());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = back[0].getY();
                for(int i = 1; i < back.length; i++) min = Math.min(min, back[i].getY());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if((!inLeft || !inRight) && front.length > 0){
                float max = front[0].getY();
                for(int i = 1; i < front.length; i++) max = Math.max(max, front[i].getY());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = front[0].getY();
                for(int i = 1; i < front.length; i++) min = Math.min(min, front[i].getY());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if((!inLeft || !inRight) && top.length > 0){
                float max = top[0].getY();
                for(int i = 1; i < top.length; i++) max = Math.max(max, top[i].getY());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = top[0].getY();
                for(int i = 1; i < top.length; i++) min = Math.min(min, top[i].getY());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if((!inLeft || !inRight) && bottom.length > 0){
                float max = bottom[0].getY();
                for(int i = 1; i < bottom.length; i++) max = Math.max(max, bottom[i].getY());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = bottom[0].getY();
                for(int i = 1; i < bottom.length; i++) min = Math.min(min, bottom[i].getY());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if(!inLeft && !inRight){
                if(t.getYMax() >= divider){
                    inRight = true;
                }
                if(t.getYMin() <= divider){
                    inLeft = true;
                }
            }
            if(inLeft)left.insert(t);
            if(inRight)right.insert(t);
            inLeft = false;
            inRight = false;
        }
        return new Voxel[]{left, right};
    }

    protected Voxel[] splitZ(Voxel v){
        float divider = (v.zMax + v.zMin)/2;
        Voxel left = new Voxel(v.xMax, v.xMin, v.yMax, v.yMin, divider, v.zMin, depth + 1);
        Voxel right = new Voxel(v.xMax, v.xMin, v.yMax, v.yMin, v.zMax, divider, depth + 1);
        boolean inLeft = false;
        boolean inRight = false;
        for(Triangle t : v.voxel){
            Vertex back[] = t.intersectX(v.xMax);
            Vertex front[] = t.intersectX(v.xMin);
            Vertex top[] = t.intersectY(v.yMax);
            Vertex bottom[] = t.intersectY(v.yMin);
            if(back.length > 0){
                float max = back[0].getZ();
                for(int i = 1; i < back.length; i++) max = Math.max(max, back[i].getZ());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = back[0].getZ();
                for(int i = 1; i < back.length; i++) min = Math.min(min, back[i].getZ());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if((!inLeft || !inRight) && front.length > 0){
                float max = front[0].getZ();
                for(int i = 1; i < front.length; i++) max = Math.max(max, front[i].getZ());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = front[0].getZ();
                for(int i = 1; i < front.length; i++) min = Math.min(min, front[i].getZ());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if((!inLeft || !inRight) && top.length > 0){
                float max = top[0].getZ();
                for(int i = 1; i < top.length; i++) max = Math.max(max, top[i].getZ());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = top[0].getZ();
                for(int i = 1; i < top.length; i++) min = Math.min(min, top[i].getZ());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if((!inLeft || !inRight) && bottom.length > 0){
                float max = bottom[0].getZ();
                for(int i = 1; i < bottom.length; i++) max = Math.max(max, bottom[i].getZ());
                if(max < divider) {
                    inLeft = true;
                } else {
                    inRight = true;
                }
                float min = bottom[0].getZ();
                for(int i = 1; i < bottom.length; i++) min = Math.min(min, bottom[i].getZ());
                if(min < divider) {
                    inLeft = true;
                }
            }
            if(!inLeft && !inRight){
                if(t.getZMax() >= divider){
                    inRight = true;
                }
                if(t.getZMin() <= divider){
                    inLeft = true;
                }
            }
            if(inLeft)left.insert(t);
            if(inRight)right.insert(t);
            inLeft = false;
            inRight = false;
        }
        return new Voxel[]{left, right};
    }

    public ArrayList<String[]> getCount(){
        ArrayList<String[]> temp = new ArrayList<>();
        temp.add(new String[]{"-1", "" + currTriangles});
        return temp;
    }

    public ArrayList<Triangle[]> findIntersection(){
        ArrayList<Triangle[]> returnThis = new ArrayList<>();
        for(int i = 0; i < currTriangles - 1; i++){
            Triangle t1 = voxel.get(i);
            for(int p = i; p < currTriangles; p++){
                Triangle t2 = voxel.get(p);
                if(!t1.getName().equals(t2.getName())){
                    if(t1.checkIntersection(t2)){
                        returnThis.add(new Triangle[]{t1, t2});
                    }
                }
            }
        }
        return returnThis;
    }
}
