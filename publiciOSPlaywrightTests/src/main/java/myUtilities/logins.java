package myUtilities;

import java.util.Map;

public class logins {

	private static final Map<String, String> TOKENS = Map.ofEntries(
			Map.entry("testing", "abcd1234")
			);
	public static String getToken(String host) {
		return TOKENS.get(host);
	}
}