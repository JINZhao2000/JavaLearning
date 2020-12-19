package leetcode.implementQueueUsingStacks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyQueueTest {
	private MyQueue myQueue;
	@Before
	public void setUp(){
		myQueue = new MyQueue();
	}
	@After
	public void tearDown(){
		myQueue = null;
	}
	@Test
	public void testEmpty(){
		assertTrue(myQueue.empty());
	}
	@Test
	public void testPush(){
		myQueue.push(1);
		assertFalse(myQueue.empty());
	}
	@Test
	public void testPop(){
		myQueue.push(1);
		myQueue.push(2);
		myQueue.push(3);
		assertEquals(1,myQueue.pop());
	}
	@Test
	public void testPeek(){
		myQueue.push(1);
		myQueue.push(2);
		myQueue.push(3);
		assertEquals(1,myQueue.peek());
	}
}
