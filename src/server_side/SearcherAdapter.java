package server_side;

import java.util.List;

public class SearcherAdapter<T> implements Solver<Searchable<T>, List<State<T>>>
{
    private Searcher<T> searcher;

    public SearcherAdapter(Searcher<T> s)
    {
        this.searcher = s;
    }


    @Override
    public List<State<T>> solve(Searchable<T> p)
    {
        return searcher.search(p);
    }
}
