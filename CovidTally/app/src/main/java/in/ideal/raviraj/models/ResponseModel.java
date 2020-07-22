package in.ideal.raviraj.models;

import java.util.List;

public class ResponseModel {

    private GlobalModel Global ;
    private List<CountryModel> Countries ;
    private String Date ;

    public GlobalModel getGlobal() {
        return Global;
    }

    public void setGlobal(GlobalModel global) {
        Global = global;
    }

    public List<CountryModel> getCountries() {
        return Countries;
    }

    public void setCountries(List<CountryModel> countries) {
        Countries = countries;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
