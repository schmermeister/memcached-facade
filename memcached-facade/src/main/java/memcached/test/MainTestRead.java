package memcached.test;

import java.util.List;

import memcached.facade.MemcachedFacade;

public class MainTestRead {

	private static final String KEY = "someKEY";

	public static void main(final String[] args) {

		final MainTestRead mt = new MainTestRead();
		// for (int i = 0; i < 1000; i++) {
		// mt.readDistributed();
		// }

		System.exit(0);

	}

	public void readDistributed(final List<String> keys, final MemcachedFacade facade) {
		// final List<String> server = new ArrayList<>();
		// server.add("193.242.210.171:11211");
		// server.add("localhost:11211");
		// final MemcachedFacade facade = new MemcachedFacade(server);

		for (final String key : keys) {
			final String testobject2 = (String) facade.getDataFrom(key);
			// if (testobject2 == null) {
			System.out.println("read local result: " + testobject2);
			// }
		}

		facade.shutdown();

	}

}
