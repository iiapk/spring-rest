package com.iiapk.rest.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	
	private int balance;
	public final Lock balanceLock = new ReentrantLock();
	
	public Account(int balance){
		this.balance=balance;
	}
	
	public boolean deposit(final int amount) throws InterruptedException{
		boolean islock = balanceLock.tryLock(1,TimeUnit.SECONDS);
		System.out.println(islock);
		if(islock){
		try{
			System.out.println(this.toString()+" depot "+amount);
			//Thread.sleep(500);
			if(amount>0){
				balance+=amount;
				return true;
			}
			return false;
			}finally{
				balanceLock.unlock();
			}
		}
		return false;
	}
	
	public boolean withdraw(final int amount){
		try{
			balanceLock.lock();
			System.out.println(this.toString()+" withdraw"+amount);
			Thread.sleep(1000);
			if(amount>0&&balance>=amount){
				balance-=amount;
				return true;
			}
			return false;
			} catch (InterruptedException e) {
				return false;
			}finally{
				balanceLock.unlock();
			}
	}

	public int getBalance() {
		return balance;
	}
	
}
