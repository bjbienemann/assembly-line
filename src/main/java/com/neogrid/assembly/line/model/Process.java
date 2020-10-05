package com.neogrid.assembly.line.model;

import java.time.Duration;
import java.util.Objects;

public class Process {

    private String title;
    private Duration duration;
    private String registry;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Process that = (Process) o;

        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(title)
                .append(" ")
                .append(duration.toMinutes())
                .append("min")
                .toString();
    }
}
