package leetcode.implementStackUsingQueues;

import java.util.ArrayDeque;
import java.util.Queue;

public class MyStack {
	private Queue<Integer> queue;
	public MyStack(){
		queue = new ArrayDeque<>();
	}
	public void push(int x){
		Queue<Integer> tempQueue = new ArrayDeque<>();
		tempQueue.add(x);
		while(!this.empty()){
			tempQueue.add(this.pop());
		}
		queue = tempQueue;
	}
	public int pop(){
		int top = this.top();
		queue.poll();
		return top;
	}
	public int top(){
		return queue.peek();
	}
	public boolean empty(){
		return queue.isEmpty();
	}
	@Override
	public String toString () {
		return queue.toString();
	}
}
