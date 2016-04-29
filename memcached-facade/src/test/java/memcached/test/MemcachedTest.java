package memcached.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

public class MemcachedTest {

	private MemcachedClient client;

	@Before
	public void setUp() {
		try {
			client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
		} catch (final IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void write() {
		final OperationFuture<Boolean> future = client.set("someKey", 3600, "someValue");
		try {
			Assert.assertTrue(future.get());
		} catch (InterruptedException | ExecutionException e) {
			fail();
		}
	}

	@Test
	public void read() {
		final String testobject = (String) client.get("someKey");
		Assert.assertEquals(testobject, "someValue");
	}

}
