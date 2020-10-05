package com.neogrid.assembly.line.test;

import com.neogrid.assembly.line.integrator.TxtFileIntegrator;
import com.neogrid.assembly.line.model.Process;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class TxtFileIntegratorTest {

    private File file;

    @Before
    public void beforeInit() {
        file = new File(this.getClass().getResource("/input.txt").getPath());
    }


    @Test
    public void anyMatchStep() throws Exception {
        List<Process> processes = TxtFileIntegrator.getInstance().fileToProductionProcesses(file);
        Assert.assertTrue(processes.stream()
                .map(Process::getTitle)
                .anyMatch("Tempering sub-zero (Heat treatment)"::equals));
    }

    @Test
    public void maintenanceIsEqual5Minutes() throws Exception {
        List<Process> processes = TxtFileIntegrator.getInstance().fileToProductionProcesses(file);
        Assert.assertEquals(5l, processes.stream()
                .filter(p -> p.getTitle().equals("Assembly line cooling -"))
                .findFirst().get().getDuration().toMinutes());
    }

}
