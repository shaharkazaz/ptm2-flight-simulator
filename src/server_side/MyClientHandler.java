package server_side;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class MyClientHandler implements ClientHandler
{
    // need to be SearcherAdapter typeof
    private Solver<Searchable<Pair<Integer, Integer>>, List<State<Pair<Integer, Integer>>>> searcherSolver;
    private CacheManager cm;

    public MyClientHandler(Solver<Searchable<Pair<Integer, Integer>>, List<State<Pair<Integer, Integer>>>> s,
                           CacheManager c)
    {
        this.searcherSolver = s;
        this.cm = c;
    }

    @Override
    public void handleClient(InputStream in, OutputStream out) throws IOException
    {
        // Initialize buffers
        BufferedReader b_in = new BufferedReader(new InputStreamReader(in));
        BufferedWriter b_out = new BufferedWriter(new OutputStreamWriter(out));

        String client_input;
        List<State<Pair<Integer, Integer>>> solution;

        int[][] matrix = new int[1][];
        int index = 0;

        while(!(client_input = b_in.readLine()).equals("end"))
        {
            matrix = Arrays.copyOf(matrix, ++index);
            try
            {
                int[]matrixLine = messageToArray(client_input);
                matrix[index - 1] = matrixLine;

            }
            catch (Exception e)
            {
                b_out.write("Wrong line" + "\n");
                b_out.flush();
            }
        }

        client_input = b_in.readLine();
        Pair<Integer, Integer> enterField = stringToPair(client_input);
        client_input = b_in.readLine();
        Pair<Integer, Integer> exitField = stringToPair(client_input);

        Searchable<Pair<Integer, Integer>> matrixDomain = new MatrixDomain(enterField, exitField, matrix);

        if(this.cm.search(matrixDomain.toString()))
        {
            b_out.write(this.cm.getSolution(matrixDomain.toString()) + "\n");
            b_out.flush();
        }
        else
        {
            solution = this.searcherSolver.solve(matrixDomain);
            String solutionForClient = solutionToClient(solution);
            this.cm.saveSolution(matrixDomain.toString(), solutionForClient);
            b_out.write(solutionForClient + "\n");
            b_out.flush();
        }
    }

    private int[] messageToArray(String input)
    {
        String[] strArray = input.split(",");
        int[] intArray = new int[strArray.length];
        for(int i = 0; i < strArray.length; i++)
        {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }

    private Pair<Integer, Integer> stringToPair(String s)
    {
        int[] splicedIndexes = messageToArray(s);
        return new Pair<>(splicedIndexes[0], splicedIndexes[1]);
    }

    private String solutionToClient(List<State<Pair<Integer, Integer>>> solution)
    {
        StringBuilder route = new StringBuilder();
        for(int i = solution.size(); i-- >1;)
        {
            Pair<Integer, Integer> fromPair = solution.get(i).getState();
            Pair<Integer, Integer> toPair = solution.get(i-1).getState();
            route.append(direction(fromPair, toPair));
            route.append(",");
        }
        route.deleteCharAt(route.length()-1);

        return route.toString();
    }

    private String direction(Pair<Integer, Integer> from, Pair<Integer, Integer> to)
    {
        if(from.getRight() - to.getRight() == 1)
            return "Left";
        else if(from.getRight() - to.getRight() == -1)
            return "Right";
        else if(from.getLeft() - to.getLeft() == 1)
            return "Up";
        else
            return "Down";
    }
}
