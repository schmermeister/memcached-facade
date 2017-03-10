package memcached.facade;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.primitives.Primitives;

import memcached.exception.MemcachedClientException;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 * Facade for memcached. Useful for storing, receiving and removing data from
 * memcached.
 * 
 * @author André Schmer
 * 
 */
public class MemcachedFacade {

	private static final int SECOND = 1;
	private static final int MINUTE = 60 * SECOND;
	private static final int HOUR = 60 * MINUTE;
	// private static final int DAY = 24 * HOUR;

	/**
	 * will never expire
	 */
	// private static final int EXP = 0;

	/**
	 * will expire after 1 hour
	 */
	private static final int EXP_P = 1 * HOUR;

	/**
	 * milliseconds
	 */
	private static final int ASYNC_TIMEOUT = 50;

	private ConnectionFactoryBuilder builder;

	private MemcachedClient client;

	/**
	 * 
	 * @param host
	 * @param port
	 */
	public MemcachedFacade(final String host, final String port) {
		try {
			registerClient(host, port);
		} catch (final MemcachedClientException e) {
			e.printStackTrace();
		}
	}

	private void registerClient(final String host, final String port) throws MemcachedClientException {
		builder = new ConnectionFactoryBuilder();
		if (false == "127.0.0.1".equals(host)) {
			builder = builder.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
		}
		builder = builder.setOpQueueMaxBlockTime(500);
		try {
			client = new MemcachedClient(builder.setFailureMode(FailureMode.Retry).build(), AddrUtil.getAddresses("localhost" + ":" + port));
		} catch (final IOException e) {
			throw new MemcachedClientException("Unable to create new cache client, cause " + e.getMessage());
		}
	}

	/**
	 * Shuts down the client.
	 */
	public void shutdown() {
		client.shutdown();
	}

	/**
	 * Removes the cache data with corresponding {@code key}.
	 *
	 * @param key
	 */
	public Boolean removeFromCache(final String key) {
		try {
			return client.delete(key).get();
		} catch (final InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	/**
	 * Sets {@code data} with {@code key} in cache. Neither {@code key} nor
	 * {@code data} may be null. In this case a NullPointerException will be
	 * thrown {@link NullPointerException}.
	 * 
	 * @param key
	 *            the key under which data is stored.
	 * @param data
	 *            the data to store
	 * @param exp
	 *            expiration time for data in seconds
	 * @return {@code Boolean.TRUE} if a future is responding,
	 *         {@code Boolean.FALSE} otherwise.
	 */
	public Boolean setDataTo(final String key, final Object data, final int exp) {
		try {
			return client.set(key, exp, data).get();
		} catch (final InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	/**
	 * Sets {@code data} with {@code key} in cache with expiration of
	 * {@value #EXP_P} seconds, regardless of any existing value. Neither
	 * {@code key} nor {@code data} may be null.
	 * 
	 * @param key
	 *            the key under which data is stored.
	 * @param data
	 *            the data to store
	 * @return {@code Boolean.TRUE} if a future is responding,
	 *         {@code Boolean.FALSE} otherwise.
	 */
	public Boolean setDataTo(final String key, final Object data) {
		try {
			final OperationFuture<Boolean> future = client.set(key, EXP_P, data);
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	/**
	 * Get the {@code data} from the cache. Uses the asynchronous approach with
	 * a timeout delay of {@value #ASYNC_TIMEOUT} ms. In case of the cache does
	 * not responding, {@code null} will be returned.
	 * 
	 * @param key
	 *            for the data value.
	 * @param clazz
	 *            , the expected type of the data value.
	 * @return the data with the type of {@code clazz}
	 */
	public <T> T getDataFrom(final String key, final Class<T> clazz) {
		final Future<Object> f = client.asyncGet(key);
		try {
			final Object object = f.get(ASYNC_TIMEOUT, TimeUnit.MILLISECONDS);
			return Primitives.wrap(clazz).cast(object);
		} catch (final InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (final TimeoutException e) {
			f.cancel(false);
		}
		return null;
	}

	/**
	 * Get the {@code data} from the cache. Uses the asynchronous approach with
	 * a timeout delay of {@value #ASYNC_TIMEOUT} ms. In case of the cache does
	 * not responding, {@code null} will be returned.
	 * 
	 * @param key
	 *            for the data value.
	 * @param clazz
	 *            , the expected type of the data value.
	 * @return the data with the type of {@code clazz}
	 */
	public Object getDataFrom(final String key) {
		final Future<Object> f = client.asyncGet(key);
		try {
			return f.get(ASYNC_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (final InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (final TimeoutException e) {
			f.cancel(false);
		}
		return null;
	}

	public Map<SocketAddress, Map<String, String>> getStats() {
		return client.getStats();
	}

}
