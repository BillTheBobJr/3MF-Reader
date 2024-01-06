package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String filename = "3DBenchy.3mf";
        String source = "src/main/java/zipped_3mf/" + filename;
        String dest = "src/main/java/unzipped_3mf";

        Spacial_View sv = new Spacial_View();

        unzip(source, dest);
        _3d_Object listOfObjs[] = _3mfReader.readModelAlt(sv);

        System.out.println(listOfObjs[0].vertices.size());

        sv.complete();

//        for(int i = 0; i < listOfObjs.length; i++){
//            System.out.println(listOfObjs[i]);
//        }
        //System.out.println(listOfObjs[0].size());
    }

    public static void unzip(String fileName, String fileDestination){
        try{
            FileInputStream filestream = new FileInputStream(fileName);
            ZipInputStream zipstream = new ZipInputStream(filestream);
            ZipEntry zipfile = zipstream.getNextEntry();
            byte[] buffer = new byte[1024];
            while(zipfile != null){
                String outputfilename = zipfile.getName();
                File outputfile = new File(fileDestination + File.separator + outputfilename);
                new File(outputfile.getParent()).mkdirs();
                FileOutputStream outputstream = new FileOutputStream(outputfile);
                int len;
                while((len = zipstream.read(buffer)) > 0) {
                    outputstream.write(buffer, 0, len);
                }
                outputstream.close();
                zipstream.closeEntry();
                zipfile = zipstream.getNextEntry();
            }
            zipstream.closeEntry();
            zipstream.close();
            filestream.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}