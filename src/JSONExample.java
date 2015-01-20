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
	
	

	public JSONExample(int id, String name, boolean admin, double cash) 
	{
		super();
		this.id = id;
		this.name = name;
		this.admin = admin;
		this.cash = cash;
	}

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
	
	@Override
	public boolean equals(Object object)
	{
		if(object.getClass() != JSONExample.class)
		{
			return false;
		}
		JSONExample jObject = (JSONExample) object;
		System.out.println(jObject);
		System.out.println(this);
		
		boolean isEqual = id == jObject.getId() && name.equals(jObject.getName()) &&
				admin == jObject.isAdmin() && cash == jObject.getCash();
		
		return isEqual;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	@Override
	public String toString() {
		return "JSONExample [id=" + id + ", name=" + name + ", admin=" + admin
				+ ", cash=" + cash + "]";
	}
	
	

}
