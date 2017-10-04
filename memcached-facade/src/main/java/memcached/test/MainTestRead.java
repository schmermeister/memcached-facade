package memcached.test;

import java.util.ArrayList;
import java.util.List;

import memcached.facade.MemcachedFacade;

public class MainTestRead {

	private static final String KEY = "someKEY";

	public static void main(final String[] args) {

		final MainTestRead mt = new MainTestRead();
		mt.readlocal();

		System.exit(0);

	}

	public void readlocal() {
		final List<String> server = new ArrayList<>();
		server.add("193.242.210.171:11211");
		server.add("localhost:11211");
		final MemcachedFacade facade = new MemcachedFacade(server);

		final String testobject2 = (String) facade.getDataFrom(KEY);
		System.out.println("read local result: " + testobject2);

		facade.shutdown();

	}

}
