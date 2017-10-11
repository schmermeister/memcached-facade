package memcached.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import memcached.facade.MemcachedFacade;

public class MainTestWrite {

	private static final String KEY = "someKEY";
	private static final String VALUE = "someVALUE-3";

	public static void main(final String[] args) {

		final List<String> server = new ArrayList<>();
		server.add("localhost:11211");
		server.add("193.242.210.171:11211");
		final MemcachedFacade facade = new MemcachedFacade(server);

		final MainTestWrite mt = new MainTestWrite();
		final MainTestRead mr = new MainTestRead();
		final List<String> keys = mt.writeDistributed(facade);

		mr.readDistributed(keys, facade);

		facade.shutdown();
		System.exit(0);
	}

	public List<String> writeDistributed(final MemcachedFacade facade) {

		final Random r = new Random();

		final List<String> keys = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			final String key = new UUID(r.nextLong(), r.nextLong()).toString();
			final Boolean bool = facade.setDataTo(key, VALUE);
			if (!bool) {
				System.out.println("written: " + bool);
			}
			keys.add(key);
		}

		// final Map<SocketAddress, Map<String, String>> stats = facade.getStats();
		// for (final Entry<SocketAddress, Map<String, String>> entry : stats.entrySet()) {
		// System.out.println(entry.getKey().toString());
		// for (final Entry<String, String> inner : entry.getValue().entrySet()) {
		// System.out.println("\t" + inner.getKey() + " : " + inner.getValue());
		// }
		//
		// }

		return keys;
	}

}
