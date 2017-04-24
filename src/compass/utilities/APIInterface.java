package compass.utilities;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import compass.exceptions.NoPropertyException;
import compass.exceptions.PropertyNotFoundException;
import us.monoid.json.JSONException;
import us.monoid.web.Resty;

public class APIInterface {

	//Checks database: if data is old call API
	public static void getAPI(String table, String name)
			throws ParseException, PropertyNotFoundException, NoPropertyException, IOException, JSONException {
		DBQuery dbQuery = new DBQuery(table, name);
		if (dbQuery.needUpdating()) {

			Object obj = getAPIObj(table, name);
			dbQuery.updateTable(table, obj.toString(), name);
			System.out.println(obj);
			
			
		} else {
			JSONParser jsonparser = new JSONParser();
			JSONObject json = (JSONObject) jsonparser.parse(dbQuery.getSummary());
			System.out.println(json);
		}
	}

	
	//Returns the JSON API object (information still needs to be extrapolated from json
	public static Object getAPIObj(String table, String name) throws ParseException, PropertyNotFoundException, NoPropertyException, IOException, JSONException {
		JSONParser parser = new JSONParser();
		Resty r = new Resty();
		Object obj = null;

		switch (table) {
		case "Weather":
			obj = parser.parse(Collector.collectWeatherData(null));
			break;
		case "Transport":
			//General travel information about singular location
			if(!name.contains("-")) { //NEED TO DYNAMICALLY CHANGE LAT AND LONG COORDS OTHERWISE WILL ALWAYS BE CARDIFF	
				obj = r.text("https://transportapi.com/v3/uk/places.json?app_id=221cce2f&app_key=d209929236fc97196775650c2bdb639e&lat=51.481583&lon=-3.179090&query=Yeovil&type=bus_stop,train_station")
						.toString();	
			} else {
			//General travel information from one destination to another
				String[] fromLocTo = name.split("-");
				obj = r.text("http://transportapi.com/uk/public/journey/from/cardiff/to/london.json").toString();		
			}
			break;
		default:
			obj = r.json("http://lookup.dbpedia.org/api/search.asmx/KeywordSearch?QueryString=" + name.replace(" ", "+")).object();
		}
		return obj;
	}
}
