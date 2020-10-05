package com.neogrid.assembly.line.test;

import com.neogrid.assembly.line.controller.Production;
import com.neogrid.assembly.line.integrator.TxtFileIntegrator;
import com.neogrid.assembly.line.model.AssemblyLine;
import com.neogrid.assembly.line.model.Process;
import com.neogrid.assembly.line.model.Step;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalTime;
import java.util.List;

public class Production2Test {

    private File file;

    @Before
    public void beforeInit() {
        file = new File(this.getClass().getResource("/input2.txt").getPath());
    }

    @Test
    public void assemblyLinesTest() throws Exception {
        Production production = new Production();
        List<Process> processes = TxtFileIntegrator.getInstance().fileToProductionProcesses(file);
        List<AssemblyLine> assemblyLines = production.createAssemblyLines(processes);

        Assert.assertTrue(assemblyLines.stream().map(AssemblyLine::getLunch)
                .allMatch(s -> s.getTime().equals(LocalTime.of(12, 0))));

        Assert.assertTrue(assemblyLines.stream()
                .anyMatch(as -> as.getAfternoon().stream()
                        .filter(a -> a.getProcess().getTitle().equals("Ginástica laboral"))
                        .map(Step::getTime)
                        .anyMatch(LocalTime.of(16, 0)::isBefore)));
    }

    @Test
    public void sumInMinutes() throws Exception {
        Production production = new Production();
        List<Process> processes = TxtFileIntegrator.getInstance().fileToProductionProcesses(file);

        Assert.assertEquals(Long.valueOf(965l), production.sumProductionProcesses(processes));
    }

    @Test
    public void divideProductionProcesses() throws Exception {
        Production production = new Production();
        List<Process> processes = TxtFileIntegrator.getInstance().fileToProductionProcesses(file);
        List<Process>[] dividedProductionProcesses = production.divideProductionProcesses(processes);

        Assert.assertEquals(3, dividedProductionProcesses.length);
    }

}
