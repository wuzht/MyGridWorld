import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HelloWorldTest extends TestCase {
	/*
	public static void main(String[] args) {
		//junit.textui.TestRunner.run(HelloWorldTest.class);
		//HelloWorld world = new HelloWorld();
		//assertEquals("Hello World!!!", world.sayHello());
	}*/

	public void testSayHello() {
		HelloWorld world = new HelloWorld();
		assertEquals("Hello World!!!", world.sayHello());
	}

	public void testGetInt() {
		HelloWorld world = new HelloWorld();
		assertEquals(21, world.getInt());
	}
}
