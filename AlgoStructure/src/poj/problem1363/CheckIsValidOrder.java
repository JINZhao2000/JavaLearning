package poj.problem1363;

import java.util.*;

public class CheckIsValidOrder {
	public static boolean checkIsValidOrder(Queue<Integer> queue){
		Stack<Integer> stack = new Stack<>();
		int n = queue.size();
		for (int i = 0; i < n; i++) {
			stack.add(i);
			while(!stack.empty()&&queue.peek()==stack.peek()){
				stack.pop();
				queue.poll();
			}
		}
		List<String> linkedList = new LinkedList<>();
		linkedList.forEach(System.out::println);
		if(!stack.empty()){
			return false;
		}
		return true;
	}
}
