package com.neogrid.assembly.line.controller;

import com.neogrid.assembly.line.model.AssemblyLine;
import com.neogrid.assembly.line.model.Process;
import com.neogrid.assembly.line.model.Step;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Production {

    public static final int MAX_MINUTES_BY_DAY = 410;

    /**
     * The production has multiple assembly lines and each one has the morning, lunch and afternoon periods.
     * @param processes
     * @return
     */
    public List<AssemblyLine> createAssemblyLines(List<Process> processes) {
        List<Process>[] dividedProductionProcesses = divideProductionProcesses(processes);

        List<AssemblyLine> assemblyLines = new ArrayList<>();
        for (List<Process> pp : dividedProductionProcesses) {
            AssemblyLine assemblyLine = new AssemblyLine();
            LocalTime initMorning = LocalTime.of(9, 0);
            LocalTime initLunch = LocalTime.of(12, 0);

            assemblyLine.setMorning(createMorning(initMorning, initLunch, pp));

            assemblyLine.setLunch(createLunch(initLunch));

            LocalTime initAfternoon = LocalTime.of(13, 0);
            assemblyLine.setAfternoon(createAfternoon(initAfternoon, pp));

            assemblyLines.add(assemblyLine);
        }

        return assemblyLines;
    }

    /**
     * The morning period begins at 9:00 am and must finish by 12:00 noon, for lunch.
     * @param start
     * @param stop
     * @param processes
     * @return
     */
    public List<Step> createMorning(LocalTime start, LocalTime stop, List<Process> processes) {
        List<Step> steps = new ArrayList<>();
        List<Process> removeProcesses = new ArrayList<>();

        LocalTime now = start;
        for (Process process : processes) {
            if (now.plusMinutes(process.getDuration().toMinutes()).compareTo(stop) > 0) {
                continue;
            }

            Step step = new Step();
            step.setTime(now);
            step.setProcess(process);

            steps.add(step);
            removeProcesses.add(process);

            now = now.plusMinutes(process.getDuration().toMinutes());
        }

        processes.removeAll(removeProcesses);
        return steps;
    }

    public Step createLunch(LocalTime now) {
        Step step = new Step();
        step.setTime(now);

        Process process = new Process();
        String lunch = "Almoço";
        process.setTitle(lunch);
        process.setRegistry(lunch);
        process.setDuration(Duration.ofHours(1));

        step.setProcess(process);

        return step;
    }

    public List<Step> createAfternoon(LocalTime start, List<Process> processes) {
        List<Step> steps = new ArrayList<>();
        LocalTime now = start;
        for (Process process : processes) {
            Step step = new Step();
            step.setTime(now);
            step.setProcess(process);
            steps.add(step);

            now = now.plusMinutes(process.getDuration().toMinutes());
        }

        steps.add(createLaborGymnasticsActivities(now));
        return steps;
    }

    /**
     * The labor gymnastics activities can start no earlier than 4:00 pm and no later than 5:00 pm.
     * @param now
     * @return
     */
    public Step createLaborGymnasticsActivities(LocalTime now) {
        Step step = new Step();
        LocalTime laborGymnasticsActivitiesMin = LocalTime.of(16, 00);

        if (now.isAfter(laborGymnasticsActivitiesMin)) {
            step.setTime(now);
        } else {
            step.setTime(laborGymnasticsActivitiesMin);
        }

        Process process = new Process();
        String lunch = "Ginástica laboral";
        process.setTitle(lunch);
        process.setRegistry(lunch);
        process.setDuration(Duration.ofHours(1));

        step.setProcess(process);

        return step;
    }

    public Long sumProductionProcesses(List<Process> processes) {
        return processes.stream()
                .map(Process::getDuration)
                .map(Duration::toMinutes)
                .reduce(0L, Long::sum);
    }

    public List<Process>[] divideProductionProcesses(List<Process> processes) {
        int countAssemblyLine = divideMaxMinutesByDay(processes).intValue();
        List<Process>[] dividedProductionProcesses = new ArrayList[countAssemblyLine];
        for (int i = 0; i < countAssemblyLine; i++) {
            dividedProductionProcesses[i] = new ArrayList<>();
            for (int ii = 0; ii < processes.size(); ii++) {
                Long sumSteps = sumProductionProcesses(dividedProductionProcesses[i]);
                if (sumSteps.compareTo(Long.valueOf(MAX_MINUTES_BY_DAY)) >= 0) {
                    break;
                }

                Long sum = sumSteps + processes.get(ii).getDuration().toMinutes();
                if (sum.compareTo(Long.valueOf(MAX_MINUTES_BY_DAY)) < 0) {
                    dividedProductionProcesses[i].add(processes.get(ii));
                }
            }
            processes.removeAll(dividedProductionProcesses[i]);
        }

        return dividedProductionProcesses;
    }

    private Long divideMaxMinutesByDay(List<Process> processes) {
        Long allMinutes = sumProductionProcesses(processes);
        return new BigDecimal(allMinutes)
                .divide(BigDecimal.valueOf(MAX_MINUTES_BY_DAY), 0, RoundingMode.UP)
                .longValue();
    }

}
