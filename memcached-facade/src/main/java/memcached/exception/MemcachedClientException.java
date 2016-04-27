package memcached.exception;

/**
 * @author schmer
 *
 */
public class MemcachedClientException extends Exception {

	private static final long serialVersionUID = -8695471592494577109L;

	public MemcachedClientException(final String msg) {
		super(msg);
	}

}
