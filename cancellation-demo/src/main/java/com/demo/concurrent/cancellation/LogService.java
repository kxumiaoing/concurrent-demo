package com.demo.concurrent.cancellation;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * serivce based threads is responsible for its threads' life
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private boolean isShutdown = false;
    private long reservations;

    public LogService(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>();
        this.logger = new LoggerThread(writer);
    }

    public void start(){
        this.logger.start();
    }

    public void stop(){
        synchronized (this) {
            this.isShutdown = true;
        }

        this.logger.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (this.isShutdown){
                throw new IllegalStateException("LogService is shutdown.");
            }

            this.reservations++;
        }

        this.queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter printer;

        public LoggerThread(Writer writer) {
            this.printer = new PrintWriter(writer);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.this) {
                            if (isShutdown && 0 == reservations){
                                break;
                            }
                        }

                        String msg = queue.take();

                        synchronized (LogService.this) {
                            reservations--;
                        }

                        this.printer.println(msg);
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                this.printer.close();
            }
        }
    }
}
