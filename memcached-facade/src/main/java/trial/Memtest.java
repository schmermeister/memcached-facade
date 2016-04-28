package trial;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

public class Memtest {

	// TODO: test in unittest auslagern
	public static void main(final String[] args) {

		MemcachedClient c;
		try {
			c = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			// Store a value (async) for one hour
			c.set("someKey", 3600, "hallo");
			// Retrieve a value (synchronously).
			final String myObject = (String) c.get("someKey");
			System.out.println(myObject);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		System.exit(0);

	}

}
