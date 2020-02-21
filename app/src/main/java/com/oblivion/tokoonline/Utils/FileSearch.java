package com.oblivion.tokoonline.Utils;

import java.io.File;
import java.util.ArrayList;

public class FileSearch {

    public static ArrayList<String> getDirectoryPath(String directory){
        ArrayList<String> pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listfiles = file.listFiles();


        for(int i = 0; i < listfiles.length; i++){
            if(listfiles[i].isDirectory()){
                pathArray.add(listfiles[i].getAbsolutePath());
            }
        }
        return pathArray;
    }


    public static ArrayList<String> getFilePath(String directory){
        ArrayList<String> pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listfiles = file.listFiles();

        if (listfiles != null){

            for(int i = 0; i < listfiles.length; i++){
                if(listfiles[i].isFile()){
                    pathArray.add(listfiles[i].getAbsolutePath());
                }
            }

        }
        return pathArray;
    }
}
