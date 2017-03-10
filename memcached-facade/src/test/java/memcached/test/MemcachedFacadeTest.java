package memcached.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import memcached.facade.MemcachedFacade;

public class MemcachedFacadeTest {

	private MemcachedFacade facade;

	@Before
	public void setUp() {
		facade = new MemcachedFacade("localhost", "11211");

	}

	@Test
	public void readwrite() {
		final Boolean bool = facade.setDataTo("someKey", "someValue");
		Assert.assertTrue(bool);

		final String testobject = (String) facade.getDataFrom("someKey");
		Assert.assertEquals(testobject, "someValue");
	}

	@Test
	public void readWriteWithExp() {
		final Boolean bool = facade.setDataTo("someKey", "someValue", 300);
		Assert.assertTrue(bool);

		final String testobject = (String) facade.getDataFrom("someKey");
		Assert.assertEquals(testobject, "someValue");
	}

	@After
	public void shutdown() {
		facade.shutdown();
	}

}
