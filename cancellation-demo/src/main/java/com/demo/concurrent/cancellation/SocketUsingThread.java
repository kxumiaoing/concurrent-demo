package com.demo.concurrent.cancellation;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * thread's non-standard cancel
 */
public class SocketUsingThread extends Thread{
    private final Socket socket;
    private final InputStream in;

    public SocketUsingThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void run() {
        try {
            byte[] buf =  new byte[1024];

            while (true) {
                int count = this.in.read(buf);

                if (count < 0){
                    break;
                } else {
                    System.out.println("wite buf to somewhere.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        try {
            this.socket.close();
        } catch (IOException e) {
        } finally {
            super.interrupt();
        }
    }
}
