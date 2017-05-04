package compass.utilities;

import compass.Constants;

public class UpdateTimeFrame {

	public static boolean updateTimeFrame(String catagory, Long lastUpdated) {

		if (lastUpdated == null) {
			return true;
		}

		Long currentTime = System.currentTimeMillis();

		switch (catagory.toLowerCase()) {
		case "weather":
			if ((lastUpdated - currentTime) > Constants.weatherUpdateTime)
				return true;
		case "transport":
			if ((lastUpdated - currentTime) > Constants.transportUpdateTime)
				return true;
		case "events":
			if ((lastUpdated - currentTime) > Constants.eventsUpdateTime)
				return true;
		case "general":
			if ((lastUpdated - currentTime) > Constants.generalUpdateTime)
				return true;
		default:
			return false;
		}
	}
}
