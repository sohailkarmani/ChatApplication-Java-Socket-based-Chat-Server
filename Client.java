package ChatApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client() {

        try {
            System.out.println("Sending Request to server");
            socket = new Socket("192.168.100.6",63793);
            System.out.println("Connection done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (Exception e) {

        }
    }

    public void startReading() {

        // thread ---read karke deta rhega
        Runnable r1 = () -> {

            System.out.println("reader started...");
            try {
                while (true) {
                    // try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server : " + msg);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connection closed");
            }
        };

        new Thread(r1).start();

    }

    public void startWriting() {
        // thread ---data user se lega and the send krega client tak
        Runnable r2 = () -> {
            System.out.println("Writer Started....");
            try {
                while (!socket.isClosed()) {
                    // try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }

                }
                System.out.println("Connection closed");
            } catch (Exception e) {
                e.printStackTrace();
            }

        };

        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("This is Client");
        new Client();
    }
}
