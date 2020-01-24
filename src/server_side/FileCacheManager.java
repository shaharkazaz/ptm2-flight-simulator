package server_side;

import java.io.*;
import java.util.HashMap;

public class FileCacheManager implements CacheManager
{

    HashMap<String, String> cache_map = null;

    public FileCacheManager()
    {
        this.cache_map = new HashMap<String, String>();

        File file = new File("hashmap.ser");
        try
        {
            if (!file.createNewFile())
            {
                System.out.println("Loading cache...");
                this.loadCacheMap();
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadCacheMap()
    {
        try
        {
            FileInputStream fis = new FileInputStream("hashmap.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.cache_map = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        }

        catch(EOFException eof)
        {
            System.out.println("Read from an empty file");
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }
        catch(ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }

        System.out.println("Deserialized HashMap");
    }

    public boolean search(String p)
    {
        return this.cache_map.containsKey(p);
    }

    public String getSolution(String p)
    {
        return this.cache_map.get(p);
    }

    public void saveSolution(String p, String s) {
        cache_map.put(p, s);

        try
        {
            FileOutputStream fos =
                    new FileOutputStream("hashmap.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cache_map);
            oos.close();
            fos.close();
            System.out.println("Serialized HashMap data is saved in hashmap.ser");
        }

        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

}
