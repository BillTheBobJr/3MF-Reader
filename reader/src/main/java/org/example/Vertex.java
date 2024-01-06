package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vertex {
    private float x;
    private float y;
    private float z;

    private String name;

    public String toString(){
        return "x = "+ x +", y = "+ y + ", z = " + z +"\n";
    }

    public static Vertex crossProduct(Vertex v1, Vertex v2){
        float x = v1.getY() * v2.getZ() - v2.getY() * v1.getZ();
        float y = v1.getZ() * v2.getX() - v2.getZ() * v1.getX();
        float z = v1.getX() * v2.getY() - v2.getX() * v1.getY();
        return new Vertex(x, y, z, "normal");
    }

    public static float dotProduct(Vertex v1, Vertex v2){
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }

        if (!(o instanceof Vertex)) {
            return false;
        }

        Vertex v = (Vertex) o;

        return this.x == v.x && this.y == v.y && this.z == v.z;
    }
}
