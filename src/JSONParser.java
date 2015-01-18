import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONParser<T> 
{
	int id;
	List<T> mList;
	private static final String SERVICE_URI = "http://tomcat.cs.lafayette.edu:3200/" ;
	DefaultHttpClient httpClient = new DefaultHttpClient();
	
	public JSONParser()
	{
		
	}
	
	public List<T> getAll(String message)
	{
		List<T> allList = query(message);
		return allList;
	}
	
	public List<T> query(String query)
	{
		List<T> qList = new ArrayList<T>();
		try {
			qList = retrieveObjects(query);
			mList = qList;
		} catch (IOException | IllegalStateException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qList;
	}
	
	public T singleQuery(String request, String tableName)
	{
		HttpResponse response;
		T single = null;
		
		try {
			response = setupJsonObject(request);
			JSONObject jsonObject = new JSONObject(readMessage(response.getEntity().getContent()));
			//jsonObject = jsonObject.getJSONObject(tableName);
			single = getJsonValues(jsonObject);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return single;
	}
	
	abstract T singleQuery(String request);
	
	/*
	private List<T> retrieveObjects(String message) throws ClientProtocolException, IOException
	{
		HttpResponse response = setupJsonObject(message);
		return new JSONParser<T>().parseJsonObjects(readMessage(response.getEntity().getContent()));
	}
	*/
	
	abstract List<T> retrieveObjects(String message) throws ClientProtocolException, IOException, IllegalStateException, JSONException;

	public String readMessage(InputStream instream) throws IOException
	{
		StringBuilder result = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(instream));
		String line;
		while ((line = r.readLine()) != null)
		{
			result.append(line);
		}
		instream.close();
		return result.toString();
	}
	
	public List<T> parseJsonObjects(String message) throws JSONException, ClientProtocolException, IOException
	{
		List<T> result = new ArrayList<T>();
		//JSONObject jsonObject = new JSONObject(message);
		//JSONArray array = jsonObject.getJSONArray(classString);
		JSONArray array = new JSONArray(message);
		for (int i = 0; i < array.length(); i++)
		{
			//JSONObject jsonobj = array.getJSONObject(i);
			result.add(parseJsonObject(array.getJSONObject(i).toString()));
		}
		return result;
	} 
	
	abstract T parseJsonObject(String message) throws JSONException, ClientProtocolException, IOException;
	
	abstract T getJsonValues(JSONObject jsonObject) throws JSONException;
	
	public HttpResponse setupJsonObject(String message) throws ClientProtocolException, IOException
	{
		String uri = SERVICE_URI + message;
		HttpGet request = new HttpGet(uri);
		request.addHeader("Accept", "application/json");
		HttpResponse response = httpClient.execute(request);
		return response;
	}
	
	public HttpResponse putJsonObject(String message, JSONObject jObject) throws ClientProtocolException, IOException
	{
		String uri = SERVICE_URI + message;
		HttpPut putRequest = new HttpPut(uri);
		putRequest.addHeader("Accept", "application/json");
		putRequest.setHeader("Content-type", "application/json");
		StringEntity input = null;
		try {
			input = new StringEntity(jObject.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		putRequest.setEntity(input);
		HttpResponse response = httpClient.execute(putRequest);
		return response;
		
	}
	
	public HttpResponse deleteRequest(String message) throws ClientProtocolException, IOException
	{
		String uri = SERVICE_URI + message;
		HttpDelete delRequest = new HttpDelete(uri);
		delRequest.setHeader("Content-type",  "application/json");
		HttpResponse response = httpClient.execute(delRequest);
		return response;
	}
	
	public HttpResponse postJsonObject(String message, JSONObject jObject) throws ClientProtocolException, IOException
	{
		String uri = SERVICE_URI + message;
		HttpPost postRequest = new HttpPost(uri);
		postRequest.addHeader("Accept", "application/json");
		postRequest.addHeader("Content-type", "application/json");
		StringEntity input = null;
		
		try {
			input = new StringEntity(jObject.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		return response;
	}
	
	abstract void putRequest(T object);
	abstract void postRequest(T object);
	
}