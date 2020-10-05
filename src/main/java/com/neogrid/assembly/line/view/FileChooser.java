package com.neogrid.assembly.line.view;

import com.neogrid.assembly.line.controller.Production;
import com.neogrid.assembly.line.integrator.TxtFileIntegrator;
import com.neogrid.assembly.line.model.AssemblyLine;
import com.neogrid.assembly.line.model.Process;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileChooser {

    public void show() {
        JFileChooser jFileChooser = newJFileChooser();

        int showOpenDialog = jFileChooser.showOpenDialog(null);
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            Production production = new Production();
            File file = jFileChooser.getSelectedFile();

            try {
                List<Process> processes = TxtFileIntegrator.getInstance().fileToProductionProcesses(file);
                List<AssemblyLine> assemblyLines = production.createAssemblyLines(processes);
                String output = TxtFileIntegrator.getInstance().outputBuilder(assemblyLines);
                saveOutputTxt(file.getParent(), output);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JFileChooser newJFileChooser() {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        jFileChooser.setDialogTitle("Select a File");
        jFileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files", "txt");
        jFileChooser.addChoosableFileFilter(filter);
        return jFileChooser;
    }

    private void saveOutputTxt(String fileParent, String outputString) throws Exception {
        Path path = Paths.get(fileParent, "output.txt");
        Files.writeString(path, outputString);
        JOptionPane.showMessageDialog(null,
                path.toAbsolutePath().toString(),
                "Output Path",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
