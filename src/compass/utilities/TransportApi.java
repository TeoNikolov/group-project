package compass.utilities;

import java.io.IOException;

import us.monoid.json.JSONException;
import us.monoid.web.Resty;

public class TransportApi {

	static Resty r = new Resty();

	public static Object transportQuery(String query) throws IOException, JSONException {

		// Return specifically train content
		if (query.contains("train")) {
			return r.json(
					"https://transportapi.com/v3/uk/train/station/CDF///timetable.json?app_id=221cce2f&app_key=d209929236fc97196775650c2bdb639e&train_status=passenger")
					.toObject();
		}

		// If the query contains two location e.g. Cardiff to London
		if (query.contains("-")) {
			String[] loc = query.split("-");
			return r.json("https://transportapi.com/v3/uk/public/journey/from/" + loc[0] + "/to/" + loc[1]
					+ ".json?app_id=221cce2f&app_key=d209929236fc97196775650c2bdb639e&service=tfl").toObject();
		}

		// Return general information about transport
		return r.json(
				"https://transportapi.com/v3/uk/places.json?app_id=221cce2f&app_key=d209929236fc97196775650c2bdb639e&lat=51.481583&lon=-3.179090&query=Cardiff&type=bus_stop,train_station")
				.toObject();

	}
}
