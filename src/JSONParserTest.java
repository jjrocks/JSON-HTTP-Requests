import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

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
		try {
			JSONObject jObject = example.readJSONFromUrl("jObject.html");			
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}

	@Test
	public void testReadJSONFromUrl() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadJSONArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadJSONArrayString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSingle() {
		fail("Not yet implemented");
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
