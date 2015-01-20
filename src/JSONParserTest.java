import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;


public class JSONParserTest {

	JSONExample example;
	
	@Before
	public void setUp() throws Exception 
	{
		example = new JSONExample("http://jjwanda.com/jsonTest/");
	}

	@Test
	public void testJSONParser() 
	{
		example = new JSONExample();
		String expected = example.getBaseURL();
		String actual = "http://localhost";
		assertEquals("Expected to get a blank base", expected, actual);
	}

	@Test
	public void testJSONParserString() 
	{
		String expected = "http://jjwanda.com/jsonTest/";
		example = new JSONExample(expected);	
		String actual = example.getBaseURL();
		assertEquals("Expected to get an example url", expected, actual);
	}

	@Test
	public void testReadJSONFromUrlString() {
		JSONObject jObject = null;
		try {
			jObject = JSONParser.readJSONFromUrl("http://jjwanda.com/jsonTest/jsonObject.html");
		} 
		catch (JSONException | IOException e) 
		{
			e.printStackTrace();
			fail();
		}
		assertNotNull(jObject);
		
	}


	@Test
	public void testReadJSONArrayString() {
		JSONArray jArray = null;
		try {
			jArray = JSONParser.readJSONArray("http://jjwanda.com/jsonTest/jsonArray.html");
		} 
		catch (JSONException | IOException e) 
		{
			e.printStackTrace();
			fail();
		}
		assertNotNull(jArray);
	}

	@Test
	public void testGetArray() 
	{
		List<JSONExample> jList = null;
		try {
			jList = example.getArray("jsonArray.html");
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int expected_size = 3;
		int actual = jList.size();
		
		assertEquals("Array did not contian 3 sizes.",expected_size, actual);
		
	}

	@Test
	public void testGetSingle() {
		JSONExample jExample = null;
		try {
			jExample = example.getSingle("jsonObject.html");
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double expected_double = 21.98;
		JSONExample expected = new JSONExample(0, "JJ Wanda", true, expected_double);
		
		assertEquals("Either the equals method is incorrect or the data wansn't parse properly", 
				expected, jExample);
	}
	
	/*

	@Test
	public void testPostRequestStringJSONObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testPostRequestString() {
		fail("Not yet implemented");
	}

	@Test
	public void testPutJSONObjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testPutRequest() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteRequest() {
		fail("Not yet implemented");
	}
	 */
}
