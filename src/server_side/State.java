package server_side;

public class State<T>
{
    private T state;
    private State<T> cameFrom;
    private double cost;
    private double fixedCost;

    public State(T state, double cost)
    {
        this.state = state;
        this.cost = cost;
        this.fixedCost = cost;
        this.cameFrom = null;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public T getState()
    {
        return state;
    }

    public State<T> getCameFrom()
    {
        return cameFrom;
    }

    public void setState(T state)
    {
        this.state = state;
    }

    public void setCameFrom(State<T> cameFrom)
    {
        this.cameFrom = cameFrom;
    }

    @Override
    public boolean equals(Object obj)
    {
        return state.equals(((State)obj).state);
    }

    public double getFixedCost()
    {
        return fixedCost;
    }
}
