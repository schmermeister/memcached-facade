package memcached.exception;

/**
 * @author André Schmer
 *
 */
public class MemcachedClientException extends Exception {

	private static final long serialVersionUID = -8695471592494577109L;

	public MemcachedClientException() {
		super();
	}

	public MemcachedClientException(final String msg) {
		super(msg);
	}

}
