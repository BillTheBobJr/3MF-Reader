package org.example;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class _3mfReader {

    public static _3d_Object[] readModelAlt(Spacial_View sv) {
        String sourcePath = "src/main/java/unzipped_3mf/3D/3dmodel.model";

        ArrayList<_3d_Object> objectList = new ArrayList<>();
        try{
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream stream = new FileInputStream(sourcePath);
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(stream);
            streamReader.next();
            while(streamReader.hasNext() && (!streamReader.isStartElement() || !streamReader.getLocalName().equals("object"))){
                streamReader.next();
            }
            for(int i = 0; streamReader.getLocalName().equals("object"); i++){
                while(!(streamReader.isEndElement() && streamReader.getLocalName().equals("object"))) {
                    objectList.add(new _3d_Object());

                    if(streamReader.isStartElement() && streamReader.getLocalName().equals("vertices")) {
                        readVertex(streamReader, objectList.get(i));
                    }

                    if(streamReader.isStartElement() && streamReader.getLocalName().equals("triangles")) {
                        readTriangles(streamReader, objectList.get(i), sv);
                    }

                    if(streamReader.isEndElement() && streamReader.getLocalName().equals("mesh")) break;

                    streamReader.next();
                }
            }

        } catch (Exception e){
            System.out.println("Reader Alt Exception: " + e);
        }

        return objectList.toArray(new _3d_Object[objectList.size()]);
    }

    private static void readVertex(XMLStreamReader streamReader, _3d_Object obj) throws XMLStreamException {
        streamReader.next();
        streamReader.next();
        int count = 0;
        do{
            float x = Float.parseFloat(streamReader.getAttributeValue("", "x"));
            float y = Float.parseFloat(streamReader.getAttributeValue("", "y"));
            float z = Float.parseFloat(streamReader.getAttributeValue("", "z"));
            Vertex v = new Vertex(x,y,z, count + "");

            obj.addVertex(v);

            count++;
            do {
                streamReader.next();
            } while(!streamReader.isStartElement() && !(streamReader.isEndElement() && streamReader.getLocalName().equals("vertices")));
        } while(streamReader.getLocalName().equals("vertex"));
        System.out.println(count);
        if(streamReader.isEndElement()) streamReader.next();
    }

    private static void readTriangles(XMLStreamReader streamReader, _3d_Object obj, Spacial_View sv) throws XMLStreamException {
        streamReader.next();
        streamReader.next();
        int count = 0;
        do{
            int v1 = Integer.parseInt(streamReader.getAttributeValue("", "v1"));
            int v2 = Integer.parseInt(streamReader.getAttributeValue("", "v2"));
            int v3 = Integer.parseInt(streamReader.getAttributeValue("", "v3"));

            Triangle t = new Triangle(obj.getVertex(v1), obj.getVertex(v2), obj.getVertex(v3), String.valueOf(obj.getId()));

            obj.addTriangle(t);

            sv.insert(t);

            count++;
            do {
                streamReader.next();
            } while(!streamReader.isStartElement() && !(streamReader.isEndElement() && streamReader.getLocalName().equals("triangles")));
        } while(streamReader.getLocalName().equals("triangle"));
        System.out.println(count);
        if(streamReader.isEndElement()) streamReader.next();
    }


    public static _3d_Object[] readModel(Spacial_View sv){
        String sourcePath = "src/main/java/unzipped_3mf/3D/3dmodel.model";

        File modelSource = new File(sourcePath);
        _3d_Object objectList[] = null;
        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbbuilder = dbfactory.newDocumentBuilder();
            Document doc = dbbuilder.parse(modelSource);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("object");
            objectList = new _3d_Object[nList.getLength()];
            for(int i = 0; i < nList.getLength() ; i++){
                NamedNodeMap objAttributes = nList.item(i).getAttributes();
                objectList[i] = new _3d_Object();

                Node node = objAttributes.getNamedItem("id");
                if(node != null){
                    objectList[i].setId(Integer.parseInt(node.getNodeValue()));
                }
                node = objAttributes.getNamedItem("pid");
                if(node != null){
                    objectList[i].setPid(Integer.parseInt(node.getNodeValue()));
                }
                Element e = (Element) nList.item(i);

                NodeList vertices = e.getElementsByTagName("vertex");
                NodeList triangles = e.getElementsByTagName("triangle");

                int count = 0;
                for(int p = 0; p < vertices.getLength(); p++){
                    NamedNodeMap attributes = vertices.item(p).getAttributes();
                    float x = Float.parseFloat(attributes.getNamedItem("x").getNodeValue());
                    float y = Float.parseFloat(attributes.getNamedItem("y").getNodeValue());
                    float z = Float.parseFloat(attributes.getNamedItem("z").getNodeValue());

                    count++;

                    if(count % 1000 == 0) System.out.println(count);

                    objectList[i].addVertex(x, y, z, count + "");
                }
                System.out.println(count);
                count = 0;

                for(int p = 0; p < triangles.getLength(); p++){
                    NamedNodeMap attributes = triangles.item(p).getAttributes();
                    int v1 = Integer.parseInt(attributes.getNamedItem("v1").getNodeValue());
                    int v2 = Integer.parseInt(attributes.getNamedItem("v2").getNodeValue());
                    int v3 = Integer.parseInt(attributes.getNamedItem("v3").getNodeValue());

                    Triangle t = new Triangle(objectList[i].getVertex(v1), objectList[i].getVertex(v2), objectList[i].getVertex(v3), "");

                    objectList[i].addTriangle(t);

                    count++;

                    sv.insert(t);
                }
                System.out.println(count);

            }
        } catch (Exception e){
            System.out.println("Reader Exception: " + e);
        }

        return objectList;


    }

}
