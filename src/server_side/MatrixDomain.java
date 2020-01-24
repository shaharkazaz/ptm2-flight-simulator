package server_side;

import java.util.ArrayList;
import java.util.Arrays;

public class MatrixDomain implements Searchable<Pair<Integer, Integer>>
{
    private int[][]matrix;
    private State<Pair<Integer, Integer>> initState;
    private State<Pair<Integer, Integer>> goalState;

    public MatrixDomain(Pair<Integer, Integer> init, Pair<Integer, Integer> goal, int[][] m)
    {
        this.matrix = m;
        this.initState = new State<>(init, getValue(init));
        this.goalState = new State<>(goal, getValue(goal));
    }

    @Override
    public State<Pair<Integer, Integer>> getInitialState()
    {
        return this.initState;
    }

    @Override
    public State<Pair<Integer, Integer>> getGoalState()
    {
        return this.goalState;
    }

    @Override
    public ArrayList<State<Pair<Integer, Integer>>> getAllPossibleStates(State<Pair<Integer, Integer>> s)
    {
        int left = s.getState().getLeft();
        int right = s.getState().getRight();

        Pair<Integer, Integer> first = new Pair<>(left + 1, right);
        Pair<Integer, Integer> second = new Pair<>(left - 1, right);
        Pair<Integer, Integer> third = new Pair<>(left, right + 1);
        Pair<Integer, Integer> fourth = new Pair<>(left, right - 1);

        ArrayList<State<Pair<Integer, Integer>>> neighbors = new ArrayList<>();
        neighbors.add(new State<>(first, getValue(first)));
        neighbors.add(new State<>(second, getValue(second)));
        neighbors.add(new State<>(third, getValue(third)));
        neighbors.add(new State<>(fourth, getValue(fourth)));

        neighbors.removeIf(temp -> temp.getCost() == -1);

        return neighbors;
    }

    private int getValue(Pair<Integer, Integer> p)
    {
        int value;
        try
        {
            value = this.matrix[p.getLeft()][p.getRight()];
            return value;
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    @Override
    public String toString()
    {
        String value =  "Matrix: '" + Arrays.deepToString(this.matrix) + "', initState: '"
                        + this.initState.getState().getLeft() + "," + this.initState.getState().getRight()
                        + "', goalState: '" + this.goalState.getState().getLeft() + "," + this.goalState.getState().getLeft() + "'";
        return value;
    }

}
