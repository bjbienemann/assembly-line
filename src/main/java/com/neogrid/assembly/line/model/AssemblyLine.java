package com.neogrid.assembly.line.model;

import java.util.ArrayList;
import java.util.List;

public class AssemblyLine {

    private List<Step> morning = new ArrayList<>();
    private Step lunch;
    private List<Step> afternoon = new ArrayList<>();;

    public List<Step> getMorning() {
        return morning;
    }

    public void setMorning(List<Step> morning) {
        this.morning = morning;
    }

    public Step getLunch() {
        return lunch;
    }

    public void setLunch(Step lunch) {
        this.lunch = lunch;
    }

    public List<Step> getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(List<Step> afternoon) {
        this.afternoon = afternoon;
    }

    public List<Step> getAll() {
        List<Step> all = new ArrayList<>();
        all.addAll(morning);
        all.add(lunch);
        all.addAll(afternoon);

        return all;
    }
}
