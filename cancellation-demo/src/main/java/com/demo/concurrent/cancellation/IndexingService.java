package com.demo.concurrent.cancellation;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * poison pill
 */
public class IndexingService {
    private static final File POISON = new File("");
    private final CrawlerThread producer = new CrawlerThread();
    private final IndexerThread consumer = new IndexerThread();
    private final BlockingQueue<File> queue;
    private final File root;

    public IndexingService(BlockingQueue<File> queue, File root) {
        this.queue = queue;
        this.root = root;
    }

    public void start(){
        this.producer.start();
        this.consumer.start();
    }

    public void stop(){
        this.producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        this.consumer.join();
    }

    private class CrawlerThread extends Thread{
        @Override
        public void run() {
            try {
                this.crawl(root);
            } catch (InterruptedException e) {
            } finally {
                while (true) {
                    try {
                        queue.put(POISON);
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void crawl(File root) throws InterruptedException {}
    }

    private class IndexerThread extends Thread{
        @Override
        public void run() {
            try {
                while (true) {
                    File file = queue.take();

                    if (POISON == file){
                        break;
                    } else {
                        this.indexFile(file);
                    }
                }
            } catch (InterruptedException e) {
            }
        }

        private void indexFile(File file) {}
    }
}