package com.neogrid.assembly.line.integrator;

import com.neogrid.assembly.line.exception.InvalidFileException;
import com.neogrid.assembly.line.model.AssemblyLine;
import com.neogrid.assembly.line.model.Process;
import com.neogrid.assembly.line.model.Step;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TxtFileIntegrator implements FileIntegrator {

    public static final String BLANK_SPACE = " ";
    public static final String EMPTY = "";

    private static FileIntegrator instance;

    public static FileIntegrator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new TxtFileIntegrator();
        }

        return instance;
    }

    @Override
    public List<Process> fileToProductionProcesses(File file) throws Exception {
        if (file.exists()) {
            try {
                List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                return lines.stream().map(this::newProductionProcess).collect(Collectors.toList());
            } catch (Exception e) {
                throw new InvalidFileException(e);
            }
        }

        return new ArrayList<>();
    }

    private Process newProductionProcess(String line) {
        String[] split = line.split(BLANK_SPACE);
        int length = split.length;
        int last = (length - 1);

        Process process = new Process();
        process.setTitle(createTitle(split, last));
        process.setDuration(createDuration(split, last));
        process.setRegistry(line);

        return process;
    }

    private String createTitle(String[] split, int last) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < last; i++) {
            sb.append(split[i]);
            sb.append(BLANK_SPACE);
        }
        return sb.toString().trim();
    }

    /**
     * All the numbers in the production step titles are the step time in minutes or the word "maintenance"
     * which one represents a 5 minutes of technical pause.
     * @param split
     * @param last
     * @return
     */
    private Duration createDuration(String[] split, int last) {
        String strMinutes = split[last].toLowerCase();
        if (strMinutes.equals("maintenance")) {
            strMinutes = "5";
        } else {
            strMinutes = strMinutes.replace("min", EMPTY);
        }

        Long minutes = Long.valueOf(strMinutes);
        return Duration.ofMinutes(minutes);
    }

    @Override
    public String outputBuilder(List<AssemblyLine> assemblyLines) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < assemblyLines.size(); i++) {
            sb.append("Linha de montagem ".concat(String.valueOf(i + 1)).concat(":\n"));
            List<Step> allSteps = assemblyLines.get(i).getAll();
            allSteps.forEach(s -> {
                sb.append(s.getTime().toString());
                sb.append(BLANK_SPACE);
                if ("Assembly line cooling -".equals(s.getProcess().getTitle())) {
                    sb.append("Esfriamento da linha manutenção");
                } else {
                    sb.append(s.getProcess().getRegistry());
                }
                sb.append("\n");
            });
            sb.append("\n");
        }
        return sb.toString().trim();
    }

}
