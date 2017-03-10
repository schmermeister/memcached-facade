package memcached.facade;

/**
 * @author André Schmer
 *
 */
public enum MemcachedClientType {

	RT_CAPPINGCACHE("runtime.capping"),

	DATA_CAMPAIGN_CAPPING("data.campaign.capping");

	private String value;

	private MemcachedClientType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
