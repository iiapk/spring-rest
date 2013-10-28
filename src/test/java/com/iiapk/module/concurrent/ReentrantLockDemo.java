package com.iiapk.module.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
	
	public static void main(String[] args) {
		final FileTest test = new FileTest();
		for(int i=0;i<5;i++){
			final int j=i+1;
			new Thread(){
				@Override
				public void run() {
					test.get(j);
				}
			}.start();
		}
		
		for(int i=0;i<5;i++){
			final int j=i+1;
			new Thread(){
				@Override
				public void run() {
					test.set(j);
				}
			}.start();
		}
	}
	
	static class FileTest{
		private ReentrantLock lock = new ReentrantLock();
		public void set(int i) {
			lock.lock();
			System.out.println("set begin"+i);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("set end"+i);
				lock.unlock();
			}
		}

		public int get(int i) {
			lock.lock();
			System.out.println("get begin"+i);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("get end"+i);
				lock.unlock();
			}
			return 1;
		}
	}
	
}
