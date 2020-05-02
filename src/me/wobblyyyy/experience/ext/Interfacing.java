package me.wobblyyyy.experience.ext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Interfacing
{
    String defaultPath;
    public Interfacing (String defaultPath)
    {
        this.defaultPath = defaultPath;
    }
    public void writeStringToJSON (String path, String string)
    {
        path = defaultPath + path;
        try
        {
            File file = new File(path);
            if (!file.exists()) file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(path);
            outputStream.write(string.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public String readStringFromJSON (String path)
    {
        path = defaultPath + path;
        try
        {
            return new String(Files.readAllBytes(Paths.get(path)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
