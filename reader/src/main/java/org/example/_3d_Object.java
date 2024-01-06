package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class _3d_Object {

    int id;

    int pid;

    ArrayList<Vertex> vertices = new ArrayList<>();

    ArrayList<Triangle> triangles = new ArrayList<>();


    public void addVertex(Vertex v){
        vertices.add(v);
    }


    public void addVertex(float x, float y, float z, String name){
        vertices.add(new Vertex(x,y,z, name));
    }

    public Vertex getVertex(int index){
        return vertices.get(index);
    }

    public void addTriangle(Vertex v1, Vertex v2, Vertex v3, String name){
        triangles.add(new Triangle(v1, v2, v3, name));
    }

    public void addTriangle(Triangle t){
        triangles.add(t);
    }

    public String toString(){
        String returnThis = "";
        for(int i = 0; i < vertices.size(); i++){
            returnThis += vertices.get(i);
        }
        for(int i = 0; i < triangles.size(); i++){
            returnThis += "v1 = " + vertices.indexOf(triangles.get(i).getV1());
            returnThis += ", v2 = " + vertices.indexOf(triangles.get(i).getV2());
            returnThis += ", v3 = " + vertices.indexOf(triangles.get(i).getV3()) + "\n";
        }

        return returnThis;
    }

    public int size(){
        return triangles.size();
    }
}
