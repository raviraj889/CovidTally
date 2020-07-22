package in.ideal.raviraj.utils;

import java.util.ArrayList;
import java.util.List;

import in.ideal.raviraj.adapter.CountryListAdapter;
import in.ideal.raviraj.models.CountryModel;
import in.ideal.raviraj.models.FiltersModel;
import in.ideal.raviraj.screens.FiltersFrag;

public class FilterList extends Thread {

    List<CountryModel> datalist;
    OnFilters onFilters;
    FiltersModel filtersModel;
    String UserCountry;

    public FilterList(String UserCountry, List<CountryModel> datalist, FiltersModel filtersModel, OnFilters onFilters){
        this.datalist = datalist;
        this.onFilters = onFilters;
        this.filtersModel = filtersModel;
        this.UserCountry = UserCountry;
    }

    @Override
    public void run() {
        super.run();

        List<CountryModel> filteredList = new ArrayList<>();

        if(filtersModel.getTC_MAX() > 0 && filtersModel.getTC_MIN() > 0){

            for(CountryModel cm : datalist){
                if(cm.getTotalConfirmed() >= filtersModel.getTC_MAX() && cm.getTotalConfirmed() <= filtersModel.getTC_MIN()){
                    filteredList.add(cm);
                }else if(cm.getCountry().equalsIgnoreCase(UserCountry)){
                    filteredList.add(0, cm);
                }
            }

        }else if(filtersModel.getTC_MAX() > 0){

            for(CountryModel cm : datalist){
                if(cm.getTotalConfirmed() >= filtersModel.getTC_MAX()){
                    filteredList.add(cm);
                }else if(cm.getCountry().equalsIgnoreCase(UserCountry)){
                    filteredList.add(0, cm);
                }
            }


        }else if(filtersModel.getTC_MIN() > 0){

            for(CountryModel cm : datalist){
                if(cm.getTotalConfirmed() <= filtersModel.getTC_MIN()){
                    filteredList.add(cm);
                }else if(cm.getCountry().equalsIgnoreCase(UserCountry)){
                    filteredList.add(0, cm);
                }
            }

        }else if(filtersModel.getTD_MAX() > 0 && filtersModel.getTD_MIN() > 0){

            for(CountryModel cm : datalist){
                if(cm.getTotalDeaths() >= filtersModel.getTD_MAX() && cm.getTotalDeaths() <= filtersModel.getTD_MIN()){
                    filteredList.add(cm);
                }else if(cm.getCountry().equalsIgnoreCase(UserCountry)){
                    filteredList.add(0, cm);
                }
            }

        }else if(filtersModel.getTD_MAX() > 0){

            for(CountryModel cm : datalist){
                if(cm.getTotalDeaths() >= filtersModel.getTD_MAX()){
                    filteredList.add(cm);
                }else if(cm.getCountry().equalsIgnoreCase(UserCountry)){
                    filteredList.add(0, cm);
                }
            }

        }else if(filtersModel.getTD_MIN() > 0){

            for(CountryModel cm : datalist){
                if(cm.getTotalDeaths() <= filtersModel.getTD_MIN()){
                    filteredList.add(cm);
                }else if(cm.getCountry().equalsIgnoreCase(UserCountry)){
                    filteredList.add(0, cm);
                }
            }

        }else if(filtersModel.getTR_MAX() > 0 && filtersModel.getTR_MIN() > 0){
            for(CountryModel cm : datalist){
                if(cm.getTotalRecovered() >= filtersModel.getTR_MAX() && cm.getTotalRecovered() <= filtersModel.getTR_MIN()){
                    filteredList.add(cm);
                }else if(cm.getCountry().equalsIgnoreCase(UserCountry)){
                    filteredList.add(0, cm);
                }
            }
        }else if(filtersModel.getTR_MAX() > 0){
            for(CountryModel cm : datalist){
                if(cm.getTotalRecovered() >= filtersModel.getTR_MAX()){
                    filteredList.add(cm);
                }else if(cm.getCountry().equalsIgnoreCase(UserCountry)){
                    filteredList.add(0, cm);
                }
            }
        }else if(filtersModel.getTR_MIN() > 0){
            for(CountryModel cm : datalist){
                if(cm.getTotalRecovered() <= filtersModel.getTR_MIN()){
                    filteredList.add(cm);
                }else if(cm.getCountry().equalsIgnoreCase(UserCountry)){
                    filteredList.add(0, cm);
                }
            }
        }

        onFilters.applyFilter(filteredList);

    }


    public interface OnFilters{
        void applyFilter(List<CountryModel> filteredList);
    }
}
