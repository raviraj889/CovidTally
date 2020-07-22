package in.ideal.raviraj.models;

public class GlobalModel {

    private long NewConfirmed ;
    private long TotalConfirmed ;
    private long NewDeaths ;
    private long TotalDeaths ;
    private long NewRecovered ;
    private long TotalRecovered ;


    public long getNewConfirmed() {
        return NewConfirmed;
    }

    public void setNewConfirmed(long newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public long getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(long totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public long getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(long newDeaths) {
        NewDeaths = newDeaths;
    }

    public long getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public long getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(long newRecovered) {
        NewRecovered = newRecovered;
    }

    public long getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(long totalRecovered) {
        TotalRecovered = totalRecovered;
    }
}
