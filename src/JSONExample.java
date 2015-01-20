import org.json.JSONException;
import org.json.JSONObject;


public class JSONExample extends JSONParser<JSONExample> 
{
	public static final String id_key = "id";
	public static final String nameKey = "name";
	public static final String adminKey = "admin";
	public static final String cashKey = "cash";
	
	private int id;
	private String name;
	private boolean admin;
	private double cash;
	
	

	public JSONExample(String string) 
	{
		super(string);
	}

	public JSONExample() 
	{
		super();
	}

	@Override
	JSONExample getJsonValues(JSONObject jsonObject) throws JSONException {
		// TODO Auto-generated method stub
		id = jsonObject.getInt(id_key);
		name = jsonObject.getString(nameKey);
		admin = jsonObject.getBoolean(adminKey);
		cash = jsonObject.getDouble(cashKey);
		return this;
	}

	@Override
	public JSONObject putJSONObjects() {
		// TODO Auto-generated method stub
		JSONObject jObject = new JSONObject();
		jObject.put(id_key, id);
		jObject.put(nameKey, name);
		jObject.put(adminKey, admin);
		jObject.put(cashKey, cash);
		return jObject;
	}

}
