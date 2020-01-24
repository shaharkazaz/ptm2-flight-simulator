package server_side;

import java.io.IOException;

public interface CacheManager
{

    boolean search(String p);
    String getSolution(String p);
    void saveSolution(String p, String s) throws IOException;

}
