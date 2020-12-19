package leetcode.implementQueueUsingStacks;

import java.util.Stack;

public class MyQueue {
	private Stack<Integer> stack;
	public MyQueue(){
		stack = new Stack<>();
	}
	public void push(int x){
		Stack<Integer> tempStack = new Stack<>();
		Stack<Integer> tempStack2 = new Stack<>();
		tempStack.push(x);
		while(!stack.empty()){
			tempStack2.push(stack.pop());
		}
		while (!tempStack2.empty()){
			tempStack.push(tempStack2.pop());
		}
		stack = tempStack;
	}
	public int pop(){
		int x = stack.peek();
		stack.pop();
		return x;
	}
	public int peek(){
		return stack.peek();
	}
	public boolean empty(){
		return stack.empty();
	}

	@Override
	public String toString () {
		return stack.toString();
	}
}
