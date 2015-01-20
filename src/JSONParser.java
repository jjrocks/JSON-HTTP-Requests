import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.net.*;
import java.nio.charset.Charset;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONParser<T> 
{
	
	private String baseURL; //This is the URL that you need to parse
	
	int id;
	List<T> mList;
	
	
	/**
	 * This class implies you're looking for local host.
	 */
	public JSONParser()
	{
		this.baseURL = "http://localhost";
	}
	
	/**
	 * When using this class it is standard that you'll be getting your json info from a base.
	 * @param url The base url. If you want to get information from google.com use http://google.com and so on.
	 */
	public JSONParser(String baseURL)
	{
		this.baseURL = baseURL;
	}
	
	/**
	 * Reads from a url and returns a single JSON Object.
	 * @param url The full url to the json object
	 * @return A JSON Object.
	 * @throws MalformedURLException If the URL isn't correct this exception occurs
	 * @throws IOException If IO magically doesn't work
	 * @throws JSONException
	 */
	public static JSONObject readJSONFromUrl(String url) throws MalformedURLException, IOException, JSONException
	{
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}
		finally
		{
			is.close();
		}
	}
	
	/**
	 * This class will read a JSON from a url given when instantiating the class.
	 * If no url is given feel free to give. 
	 * @return Returns a JSONObject to be used for other parts.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONObject readJSONFromUrl() throws MalformedURLException, IOException, JSONException
	{
		InputStream is = new URL(baseURL).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}
		finally
		{
			is.close();
		}
	}
	
	/**
	 * Is exactly like the static function except uses the baseURL instead.
	 * @return 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONArray readJSONArray() throws MalformedURLException, IOException, JSONException
	{
		return readJSONArray(baseURL);
	}
	
	/**
	 * Takes a url and returns a parsed JSONArray	
	 * @param urlAppendage
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public static JSONArray readJSONArray(String urlAppendage) throws JSONException, IOException
	{
		InputStream is = new URL(urlAppendage).openStream();
		try
		{
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		}
		finally
		{
			is.close();
		}
	}
	
	/**
	 * This will read all information from a buffered reader and put it into a string.
	 * @param rd The reader with the information
	 * @return A string of all information
	 * @throws IOException If we cannot read from the reader we will throw an exception
	 */
	private static String readAll(BufferedReader rd) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		while((cp = rd.read()) != -1)
		{
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	/**
	 * This is the method you need to implement. The JSON Values will be the ones in here.
	 * This is your chance to do whatever with the json values.
	 * @param jsonObject
	 * @return This will return the object you want to instantiate.
	 * @throws JSONException
	 */
	abstract T getJsonValues(JSONObject jsonObject) throws JSONException;
	
	/**
	 * This will get a list of all the json objects. This is used if the the object is in an array.
	 * @param urlAppendage url containing the json objects.
	 * @return List of objects after they're converted to whatever you convert them too.
	 * @throws JSONException This is thrown if this is an object or possibly not a json object
	 * @throws IOException 
	 */
	public List<T> getArray(String urlAppendage) throws JSONException, IOException
	{
		List<T> allList = new ArrayList<T>();
		JSONArray jArray = readJSONArray(urlAppendage);
		for(int i = 0; i < jArray.length(); i++)
		{
			allList.add(getJsonValues(jArray.getJSONObject(i)));
		}
		return allList;
	}

	/**
	 * This will get a single JSON Object and parse it into the specified "T" object.
	 * @param urlAppendage This is in addition to the base url
	 * @return Gets specified object.
	 * @throws MalformedURLException Throws this exception if urlAppendage does not go to a proper url
	 * @throws IOException
	 * @throws JSONException The url works but the address does not have a JSON output.
	 */
	public T getSingle(String urlAppendage) throws MalformedURLException, IOException, JSONException
	{
		JSONObject jsonObject = readJSONFromUrl(baseURL + urlAppendage);
		T item = this.getJsonValues(jsonObject);
		return item;
	}
	
	public void postRequest(String urlAppendage, JSONObject object) throws MalformedURLException, IOException
	{
		String url = baseURL + urlAppendage;
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		StringEntity entity = null;
		entity = new StringEntity(object.toString());
		
		
	}
	
	public void postRequest(String urlAppendage) throws MalformedURLException, IOException
	{
		JSONObject jObject = putJSONObjects();
		postRequest(urlAppendage, jObject);
	}
	
	public abstract JSONObject putJSONObjects();
	
	public void putRequest(String urlAppendage, JSONObject jObject) throws ClientProtocolException, IOException
	{
		HttpPut putRequest = new HttpPut(baseURL + urlAppendage);
		baseRequest(urlAppendage, putRequest, jObject);
		
	}
	
	private void baseRequest(String urlAppendage, HttpEntityEnclosingRequestBase requestBase, JSONObject jObject) throws ClientProtocolException, IOException
	{
		HttpClient httpClient = HttpClients.createDefault();
		requestBase.addHeader("Accept", "application/json");
		requestBase.setHeader("Content-type", "application/json");
		StringEntity entity = new StringEntity(jObject.toString());
		requestBase.setEntity(entity);
		httpClient.execute(requestBase);
	}
	
	public HttpResponse deleteRequest(String urlAppendage) throws ClientProtocolException, IOException
	{
		String uri = baseURL + urlAppendage;
		HttpClient httpClient = HttpClients.createDefault();
		HttpDelete delRequest = new HttpDelete(uri);
		delRequest.setHeader("Content-type",  "application/json");
		HttpResponse response = httpClient.execute(delRequest);
		return response;
	}
	
}