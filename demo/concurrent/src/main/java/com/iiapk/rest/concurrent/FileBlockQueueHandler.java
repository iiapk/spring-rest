package com.iiapk.rest.concurrent;

import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class FileBlockQueueHandler {

	private FileBlockQueueWriter fileWriter;
	private ExecutorService service;
	private Charset charSet;
	private final  BlockingQueue<byte[]> blockingQueue;
	private AtomicLong flag;

	public FileBlockQueueHandler() {
		service = Executors.newFixedThreadPool(10);
		fileWriter = FileBlockQueueWriter.createInstance("out.log");
		blockingQueue = fileWriter.getBlockingQueue();
		charSet = Charset.forName("utf-8");
		flag = new AtomicLong();
	}

	public void writeOnce(String line) {
		System.out.println("begin put"+line);
		if (!line.contains("\n"))
			line += "\n";
		try {
			//blockingQueue.put(line.getBytes(charSet));
			blockingQueue.offer(line.getBytes(charSet),2,TimeUnit.SECONDS);
			System.out.println("after put blockingQueue.size="+blockingQueue.size());
		} catch (InterruptedException e) {
			System.out.println("InterruptedException"+line);
		}
	}

	public void testBacthWrite() {
		for (int i = 0; i < 30; i++) {
			final int j = i;
			flag.incrementAndGet();
			service.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					writeOnce(""+j);
				}
			});
		}
		try {
			//wait for productor run first...
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fileWriter.write(flag);
		service.shutdown();
	}

	public static void main(String[] args) {
		FileBlockQueueHandler fileHandler = new FileBlockQueueHandler();
		fileHandler.testBacthWrite();
	}
}
