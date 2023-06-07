public abstract class FlightDistance {
    public abstract String toString(int i);

    public abstract String[] calculateDistance(double lat1, double lon1, double lat2, double lon2);
    /*
     * lat1 origin city/airport latitude
     * lon1 origin city/airport longitude
     * lat2 destination city/airport latitude
     * lon2 destination city/airport longitude
     */

}
