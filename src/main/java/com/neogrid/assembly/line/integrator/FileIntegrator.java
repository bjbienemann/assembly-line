package com.neogrid.assembly.line.integrator;

import com.neogrid.assembly.line.model.AssemblyLine;
import com.neogrid.assembly.line.model.Process;

import java.io.File;
import java.util.List;

public interface FileIntegrator {

    List<Process> fileToProductionProcesses(File file) throws Exception;

    String outputBuilder(List<AssemblyLine> assemblyLines);
}
