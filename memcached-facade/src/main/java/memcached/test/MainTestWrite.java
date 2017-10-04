package memcached.test;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import memcached.facade.MemcachedFacade;

public class MainTestWrite {

	private static final String KEY = "someKEY";
	private static final String VALUE = "someVALUE-3";

	public static void main(final String[] args) {

		final MainTestWrite mt = new MainTestWrite();
		mt.writeReplicated();

		System.exit(0);

	}

	public void writeReplicated() {
		final List<String> server = new ArrayList<>();
		server.add("193.242.210.171:11211");
		server.add("localhost:11211");
		final MemcachedFacade facade = new MemcachedFacade(server);
		final Boolean bool = facade.setDataTo(KEY, VALUE);
		System.out.println("written: " + bool);

		final Map<SocketAddress, Map<String, String>> stats = facade.getStats();
		for (final Entry<SocketAddress, Map<String, String>> entry : stats.entrySet()) {
			System.out.println(entry.getKey().toString());
			for (final Entry<String, String> inner : entry.getValue().entrySet()) {
				System.out.println("\t" + inner.getKey() + " : " + inner.getValue());
			}

		}

		facade.shutdown();
	}

}
