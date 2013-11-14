package com.iiapk.rest.concurrent;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class FileBlockQueueWriter {

	private FileChannel fileChannel;
	private ReentrantLock lock;
	private ByteBuffer byteBuffer;
	private BlockingQueue<byte[]> blockingQueue;
	private List<byte[]> data;
	private String file;
	public final static int DATASIZE = 10;
	private static FileBlockQueueWriter fileWriter;

	private FileBlockQueueWriter(String file) {
		this.file = file;
		lock = new ReentrantLock();
		blockingQueue = new LinkedBlockingQueue<byte[]>(10);
		data = new ArrayList<byte[]>();
	}

	public static FileBlockQueueWriter createInstance(String file) {
		if (fileWriter == null)
			fileWriter = new FileBlockQueueWriter(file);
		return fileWriter;
	}

	public void write(AtomicLong flag) {
		try {
			fileChannel = new RandomAccessFile(new File(file), "rws").getChannel();
			while (flag.get() > 0 || blockingQueue.size() > 0) {
				System.out.println("begin take");
				data.add(blockingQueue.poll(1, TimeUnit.SECONDS));
				System.out.println("after take" + blockingQueue.size());
				if (data.size() == DATASIZE) {
					int len = 0;
					for (byte[] dd : data) {
						len += dd.length;
					}
					byte[] unit = new byte[len];
					int destLen = 0;
					for (byte[] src : data) {
						System.arraycopy(src, 0, unit, destLen, src.length);
						destLen += src.length;
					}
					appendData(unit);
					data.clear();
				}
				flag.decrementAndGet();
				System.out.println("flag.get()=" + flag.get() + ",blockingQueue.size="+ blockingQueue.size());
			}
			fileChannel.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendData(byte[] data) throws InterruptedException,
			IOException {
		if (lock.tryLock(1, TimeUnit.SECONDS)) {
			try {
				byteBuffer = ByteBuffer.wrap(data);
				fileChannel.write(byteBuffer);
				byteBuffer.clear();
			} finally {
				lock.unlock();
				// fileChannel.close();
			}
		}
	}

	public BlockingQueue<byte[]> getBlockingQueue() {
		return blockingQueue;
	}

}
