package com.example.zhanzhou_final;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {
    public static File createFile(File folder, String fileName) {
        return new File(folder,fileName);
    }

    public static void saveObject(File fileId, Object object) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileId);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);

            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object loadObject(File fileId){
        Object object = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileId);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            object = objectInputStream.readObject();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
