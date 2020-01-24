package server_side;

import java.util.List;

public interface Searcher<T>
{
    List<State<T>> search(Searchable<T> s);
    int getNumberOfNodesEvaluated();
}
