package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class VoxelSet extends Spacial_Context{

    private HashMap<Integer, Spacial_Context> voxelSet;

    public VoxelSet(float xMax, float xMin, float yMax, float yMin, float zMax, float zMin){
        voxelSet = new HashMap<>();
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;
        this.zMax = zMax;
        this.zMin = zMin;
    }

    public void addVoxel(int key, Voxel v){
        voxelSet.put(key, v);
    }

    @Override
    public void insert(Triangle t) {
    }

    @Override
    public Spacial_Context split() {
        for(Integer i : voxelSet.keySet()){
            Spacial_Context v = voxelSet.get(i);
            Spacial_Context vs = v.split();
            if(vs != null){
                voxelSet.put(i, vs);
                vs.split();
            }
        }
        return null;
    }

    public ArrayList<String[]> getCount(){
        ArrayList<String[]> temp = new ArrayList<>();
        for(Integer i : voxelSet.keySet()){
            ArrayList<String[]> thing = voxelSet.get(i).getCount();
            for(String[] s : thing){
                if(s[0].equals("-1")){
                    temp.add(new String[]{"" + i, s[1]});
                } else {
                    temp.add(new String[]{i + s[0], s[1]});
                }
            }
        }
        return temp;
    }

    public ArrayList<Triangle[]> findIntersection(){
        ArrayList<Triangle[]> intersecting_triangles = new ArrayList<>();
        for(Integer i : voxelSet.keySet()) {
            Spacial_Context v = voxelSet.get(i);
            intersecting_triangles.addAll(v.findIntersection());
        }
        return intersecting_triangles;
    }
}
