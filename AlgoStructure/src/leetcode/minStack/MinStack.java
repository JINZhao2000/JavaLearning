package leetcode.minStack;

import java.util.Stack;

public class MinStack {
	private Stack<Integer> stack;
	private Stack<Integer> minStack;
	public MinStack(){
		stack = new Stack<>();
		minStack = new Stack<>();
	}
	public void push(int x){
		if(stack.empty()){
			minStack.push(x);
		}else{
			if(x<minStack.peek()){
				minStack.push(x);
			}else{
				minStack.push(minStack.peek());
			}
		}
		stack.push(x);
	}
	public void pop(){
		stack.pop();
		minStack.pop();
	}
	public int peek(){
		return stack.peek();
	}
	public int getMin(){
		return minStack.peek();
	}
	public boolean empty(){
		return stack.empty();
	}

	@Override
	public String toString () {
		return stack.toString()+"\n"+minStack.toString();
	}
}
