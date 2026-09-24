// Created 07-05-2026
// Updated 08-05-2026
// Written by Christopher Alton
// This is a method to call the execution report link after Playwright finishes its test

package myUtilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class perfectoReportHelperV1 {

	public static String getLatestReportUrl(String host, String jobname, String token) {
		try {

			String url = "https://" + host + ".app.perfectomobile.com/export/api/v3/test-executions" + "?jobName[0]="
					+ URLEncoder.encode(jobname, "utf-8");

//            HttpURLConnection conn = (HttpURLConnection) new java.net.URL(url).openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("PERFECTO-AUTHORIZATION", token.trim());

			HttpURLConnection conn = (HttpURLConnection) new java.net.URI(url).toURL().openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("PERFECTO-AUTHORIZATION", token.trim());

			int code = conn.getResponseCode();

			InputStream stream = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();

			StringBuilder response = new StringBuilder();
			if (stream != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			}

			JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
			JsonArray resources = json.getAsJsonArray("resources");

			if (resources != null && resources.size() > 0) {
				String id = resources.get(0).getAsJsonObject().get("id").getAsString();
				return "https://" + host + ".app.perfectomobile.com/reporting/test/" + id;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}