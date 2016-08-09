package memcached.facade;

/**
 * @author André Schmer
 *
 */
public enum MemcachedClientType {

	// RT_USERCACHE("runtime.user"),

	RT_CAPPINGCACHE("runtime.capping"),

	// DATA_ADSPACE("data.adspace"),

	// DATA_PROXY("data.proxy"),

	DATA_CAMPAIGN_CAPPING("data.campaign.capping");

	// DATA_CURRENCY("data.currency"),

	// DATA_CAPPINGDATA("data.cappingdata");

	// DATA_FORECAST("data.forecast");

	private String value;

	private MemcachedClientType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
