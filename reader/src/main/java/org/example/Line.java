package org.example;

public class Line {


    private Vertex vector;
    private Vertex normal;
    private Vertex v1;
    private Vertex v2;


    public Line(Vertex v1, Vertex v2){
        this.v1 = v1;
        this.v2 = v2;
        float x = v2.getX() - v1.getX();
        float y = v2.getY() - v1.getY();
        float z = v2.getZ() - v1.getZ();

        vector = new Vertex(x, y, z, "vector");
    }

    public Line(Vertex vector, Vertex point, int i){
        this.vector = vector;
        this.v1 = point;
        float x = v1.getX() + vector.getX();
        float y = v1.getY() + vector.getY();
        float z = v1.getZ() + vector.getZ();

        this.v2 = new Vertex(x, y, z, "point");
    }

    public Vertex getByX(float x){
        if(x < Math.min(v1.getX(), v2.getX()) || x > Math.max(v1.getX(), v2.getX())) return null;
        float t = (x - v1.getX())/ vector.getX();
        return new Vertex(vector.getX(), v1.getY() + vector.getY()*t, v1.getZ() + vector.getZ()*t, "temp");
    }

    public Vertex getByY(float y){
        if(y < Math.min(v1.getY(), v2.getY()) || y > Math.max(v1.getY(), v2.getY())) return null;
        float t = (y - v1.getY())/vector.getY();
        return new Vertex(v1.getX() + vector.getX()*t, vector.getY(), v1.getZ() + vector.getZ()*t, "temp");
    }

    public Vertex getByZ(float z){
        if(z < Math.min(v1.getZ(), v2.getZ()) || z > Math.max(v1.getZ(), v2.getZ())) return null;
        float t = (z - v1.getZ())/vector.getZ();
        return new Vertex(v1.getX() + vector.getX()*t, v1.getY() + vector.getY()*t, vector.getZ(), "temp");
    }


    public String toString(){
        return v1.toString() + v2.toString() + "<" + vector.getX() + ", " + vector.getY() + ", " + vector.getZ() + ">";
    }

    public Vertex intersect(Line l){
        try{
            float x = (l.v1.getX() - this.v1.getX())/(this.vector.getX() - l.vector.getX());
            float y = (l.v1.getY() - this.v1.getY())/(this.vector.getY() - l.vector.getY());
            float z = (l.v1.getZ() - this.v1.getZ())/(this.vector.getZ() - l.vector.getZ());
            if(this.getByX(x) != null) return new Vertex(x, y, z, "point");
        } catch (Exception e){

        }
        return null;
    }
}
