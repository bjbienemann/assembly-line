package com.neogrid.assembly.line.model;

import java.time.LocalTime;

public class Step {

    private LocalTime time;
    private Process process;

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

}
