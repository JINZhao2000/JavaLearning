package leetcode.implementStackUsingQueues;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyStackTest {
	private MyStack myStack;
	@Before
	public void setUp(){
		myStack = new MyStack();
	}
	@After
	public void tearDown(){
		myStack = null;
	}
	@Test
	public void testEmpty(){
		assertTrue(myStack.empty());
	}
	@Test
	public void testPush(){
		myStack.push(1);
		assertFalse(myStack.empty());
	}
	@Test
	public void testPop(){
		myStack.push(1);
		myStack.push(2);
		myStack.push(3);
		assertEquals(3,myStack.pop());
	}
	@Test
	public void testTop(){
		myStack.push(1);
		myStack.push(2);
		myStack.push(3);
		assertEquals(3,myStack.top());
	}
}
