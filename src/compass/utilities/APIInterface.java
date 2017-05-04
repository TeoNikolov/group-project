package compass.utilities;

import java.io.IOException;

import compass.Constants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import compass.exceptions.NoPropertyException;
import compass.exceptions.PropertyNotFoundException;
import us.monoid.json.JSONException;
import us.monoid.web.Resty;

public class APIInterface {

	public static String getAPI(String table, String name) throws JSONException, PropertyNotFoundException,
			NoPropertyException, ParseException, IOException {
		return getAPI(table, name, false);
	}

	//Checks database: if data is old call API
	public static String getAPI(String table, String name, boolean forceAPIPull)
			throws ParseException, PropertyNotFoundException, NoPropertyException, IOException, JSONException {
		DBQuery dbQuery = new DBQuery(table, name);

		if (!forceAPIPull && dbQuery.needUpdating()) {
			Object obj = getAPIObj(table, name);
			dbQuery.updateTable(table, obj.toString(), name);
			return obj.toString();
		} else {
			JSONParser jsonparser = new JSONParser();
			JSONObject json = (JSONObject) jsonparser.parse(dbQuery.getSummary());
			return json.toJSONString();
		}
	}

	
	//Returns the JSON API object (information still needs to be extrapolated from json
	public static String getAPIObj(String table, String name) throws ParseException, PropertyNotFoundException, NoPropertyException, IOException, JSONException {
		JSONParser parser = new JSONParser();
		Resty r = new Resty();
		Object obj = null;

		switch (table) {
            case "Weather":
                obj = parser.parse(Collector.collectWeatherData());
                break;
            case "Transport":
                obj = TransportApi.transportQuery(name);
                break;
            
            //Displays events in Cardiff
            case "Events":
            	obj = r.json("http://www.skiddle.com/api/v1/events/search/?api_key=60fff357ca39f4042fa5ed49a8a2b7d3&latitude=51.4816&longitude=-3.1791&radius=1&order=distance&description=1&limit=3").toObject();
            	break;
            default:
                obj = r.json("http://lookup.dbpedia.org/api/search.asmx/KeywordSearch?QueryString=" + name.replace(" ", "+")).object();
            }
		return obj.toString();
	}
}
