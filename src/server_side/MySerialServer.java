package server_side;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MySerialServer implements Server
{
    private static ServerSocket listener = null;
    private static boolean _run = true;

    public void start(int port, ClientHandler c)
    {
        try
        {
            this.listener = new ServerSocket(port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        System.out.println("Serial-Server started listening...");

        Runnable runnable = () ->
        {
            while(this._run)
            {
                try
                {
                    //this.listener.setSoTimeout(100000);
                    this.listener.setSoTimeout(1000);
                    Socket s = this.listener.accept();
                    System.out.println("Connection established.");

                    c.handleClient(s.getInputStream(), s.getOutputStream());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                catch (IOException e) { }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void stop()
    {
        this._run = false;

        try
        {
            listener.close();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
