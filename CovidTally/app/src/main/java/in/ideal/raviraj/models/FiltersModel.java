package in.ideal.raviraj.models;

import java.util.Random;

public class FiltersModel {

    private long TC_MAX;
    private long TC_MIN;
    private long TD_MAX;
    private long TD_MIN;
    private long TR_MAX;
    private long TR_MIN;

    public long getTC_MAX() {
        return TC_MAX;
    }

    public void setTC_MAX(long TC_MAX) {
        this.TC_MAX = TC_MAX;
    }

    public long getTC_MIN() {
        return TC_MIN;
    }

    public void setTC_MIN(long TC_MIN) {
        this.TC_MIN = TC_MIN;
    }

    public long getTD_MAX() {
        return TD_MAX;
    }

    public void setTD_MAX(long TD_MAX) {
        this.TD_MAX = TD_MAX;
    }

    public long getTD_MIN() {
        return TD_MIN;
    }

    public void setTD_MIN(long TD_MIN) {
        this.TD_MIN = TD_MIN;
    }

    public long getTR_MAX() {
        return TR_MAX;
    }

    public void setTR_MAX(long TR_MAX) {
        this.TR_MAX = TR_MAX;
    }

    public long getTR_MIN() {
        return TR_MIN;
    }

    public void setTR_MIN(long TR_MIN) {
        this.TR_MIN = TR_MIN;
    }
}
