package com.neogrid.assembly.line.test;

import com.neogrid.assembly.line.controller.Production;
import com.neogrid.assembly.line.exception.InvalidFileException;
import com.neogrid.assembly.line.integrator.TxtFileIntegrator;
import com.neogrid.assembly.line.model.AssemblyLine;
import com.neogrid.assembly.line.model.Process;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class InvalidFileIntegratorTest {

    private File file;

    @Before
    public void beforeInit() {
        file = new File(this.getClass().getResource("/input3.txt").getPath());
    }

    @Test(expected = InvalidFileException.class)
    public void invalidFileTest() throws Exception {
        Production production = new Production();
        List<Process> processes = TxtFileIntegrator.getInstance().fileToProductionProcesses(file);
        List<AssemblyLine> assemblyLines = production.createAssemblyLines(processes);
    }
}
