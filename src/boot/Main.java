package boot;

import server_side.*;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        CacheManager cm = new FileCacheManager();

        Searcher<Pair<Integer,Integer>> BFS = new BestFirstSearch<>();

        Solver<Searchable<Pair<Integer, Integer>>,
                List<State<Pair<Integer, Integer>>>> s = new SearcherAdapter<>(BFS);

        ClientHandler client = new MyClientHandler(s, cm);
        Server server = new MySerialServer();

        server.start(9876, client);
    }
}
