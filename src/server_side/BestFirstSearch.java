package server_side;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BestFirstSearch<T> extends CommonSearcher<T>
{
    @Override
    public List<State<T>> search(Searchable<T> s)
    {
        addToOpenList(s.getInitialState());
        this.evaluatedNodes++;

        HashSet<State<T>> closedSet = new HashSet<State<T>>();
        double currentCost;

        while(openList.size() > 0)
        {
            State<T> n = popOpenList();
            closedSet.add(n);

            if(n.equals(s.getGoalState()))
            {
                this.clearAll();
                return backTrace(n);
            }

            ArrayList<State<T>> successors = s.getAllPossibleStates(n);
            successors.removeIf(temp -> closedSet.contains(temp));

            for(State state : successors)
            {
                currentCost = n.getCost() + state.getFixedCost();
                if(!closedSet.contains(state) && !this.openList.contains(state))
                {
                    this.evaluatedNodes++;
                    state.setCost(currentCost);

                    state.setCameFrom(n);
                    addToOpenList(state);
                }
                else
                {
                    if(state.getCost() > currentCost)
                    {
                        if(!this.openList.contains(state))
                        {
                            addToOpenList(state);
                        }
                        else
                        {
                            this.openList.remove(state);
                            state.setCost(currentCost);
                            state.setCameFrom(n);
                            addToOpenList(state);
                        }
                    }
                }
            }
        }

        return null;
    }

    protected State<T> popOpenList()
    {
        return this.openList.poll();
    }

    protected void addToOpenList(State<T> s)
    {
        this.openList.add(s);
    }

    private ArrayList<State<T>> backTrace(State<T> goalState)
    {
        State<T> currentState = goalState;
        ArrayList<State<T>> routeList = new ArrayList<State<T>>();

        while(currentState.getCameFrom() != null)
        {
            routeList.add(currentState);
            currentState = currentState.getCameFrom();
        }
        routeList.add(currentState);

        return routeList;
    }

    private void clearAll()
    {
        this.openList.clear();
        this.evaluatedNodes = 0;
    }
}
