package com.iiapk.module.concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class FileWriter {
	public final static int DATASIZE = 5;
	RandomAccessFile file;
	Charset charSet;
	// BlockingQueue<byte[]> blockingQueue = new
	// LinkedBlockingQueue<byte[]>(DATASIZE);
	static FileWriter fileWriter;
	private ReentrantLock lock;
	ByteBuffer byteBuffer;

	private FileWriter(String fileName){
		try {
			file = new RandomAccessFile(new File(fileName), "rws");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		charSet = Charset.forName("utf-8");
		lock = new ReentrantLock();
	}

	public static FileWriter createInstance(String fileName) {
		if (fileWriter == null)
			fileWriter = new FileWriter(fileName);
		return fileWriter;
	}

	public void appendData(String line) throws InterruptedException, IOException {
		FileChannel fileChannel = file.getChannel();
		if (!line.contains("\n"))
			line += "\n";
		if (lock.tryLock(1, TimeUnit.SECONDS)) {
			try {
				byteBuffer = ByteBuffer.wrap(line.getBytes(charSet));
					fileChannel.write(byteBuffer);
					byteBuffer.clear();
			} finally {
				lock.unlock();
				//fileChannel.close();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		FileWriter fileWriter = FileWriter.createInstance("out.log");
		fileWriter.appendData("sdfsdfdsf");
		fileWriter.appendData("sdfsd随碟附送的冯绍峰");
	}

}
