package com.iiapk.module.concurrent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AccountCurrentTest {

	public void testnewFixedThreadPool()throws InterruptedException,ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<Callable<Integer>> list = new ArrayList<Callable<Integer>>();
		for (int i = 0; i < 20; i++) {
			final int j = i;
			list.add(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					Thread.sleep(2000);
					System.out.println("thread-" + j);
					return j;
				}
			});
		}
		List<Future<Integer>> results = executorService.invokeAll(list);
		for (Future<Integer> future : results) {
			System.out.println("thread-" + future.get());
		}
		executorService.shutdown();
	}
	
	public void testTryLock() throws InterruptedException, ExecutionException{
		final CountDownLatch begin = new CountDownLatch(1);  
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<Callable<Integer>> list = new ArrayList<Callable<Integer>>();
		final Account account = new Account(100);
		for (int i = 0; i < 5; i++) {
			final int j = i;
			list.add(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					//Account account = new Account(100);
					 begin.await();  
					account.deposit(j+1);
					//account.withdraw(j+1);
					return account.getBalance();
				}
			});
		}
		begin.countDown();  
		List<Future<Integer>> results = executorService.invokeAll(list);
		for (Future<Integer> future : results) {
			System.out.println("account-" + future.get());
		}
		executorService.shutdown();
		System.out.println("account-final " + account.getBalance());
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		new AccountCurrentTest().testTryLock();
	}

}
