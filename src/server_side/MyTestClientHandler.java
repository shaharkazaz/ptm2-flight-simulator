package server_side;

import java.io.*;

public class MyTestClientHandler implements ClientHandler
{

    Solver<String, String> solver;
    CacheManager cm;

    public MyTestClientHandler(Solver<String, String> s, CacheManager c)
    {
        this.solver = s;
        this.cm = c;
    }

    @Override
    public void handleClient(InputStream in, OutputStream out)
            throws IOException, NullPointerException
    {
        // Initialize buffers
        BufferedReader b_in = new BufferedReader(new InputStreamReader(in));
        BufferedWriter b_out = new BufferedWriter(new OutputStreamWriter(out));

        String client_input;
        String solution;

        while(!(client_input = b_in.readLine()).equals("end"))
        {
            System.out.println("From client: " + client_input );

            if(this.cm.search(client_input))
            {
                b_out.write(this.cm.getSolution(client_input) + "\n");
                b_out.flush();
            }

            else
            {
                solution = this.solver.solve(client_input);
                this.cm.saveSolution(client_input, solution);
                b_out.write(solution + "\n");
                b_out.flush();
            }

            b_out.flush();
        }
        b_in.close();
        b_out.close();
    }

}


