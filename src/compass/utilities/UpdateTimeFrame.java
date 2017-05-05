package compass.utilities;

import compass.Constants;

public class UpdateTimeFrame {

	/** Depending on category, marks the database up for updating if the time between updates exceeds the threshold.
	 * @param category the category to get the threshold of
	 * @param lastUpdated the timestamp of the last update
	 * @return true if database should be updated, false otherwise
	 */
	public static boolean updateTimeFrame(String category, Long lastUpdated) {

		if (lastUpdated == null) {
			return true;
		}

		Long currentTime = System.currentTimeMillis();

		switch (category.toLowerCase()) {
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
