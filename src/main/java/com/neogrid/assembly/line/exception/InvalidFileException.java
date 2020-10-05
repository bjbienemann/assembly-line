package com.neogrid.assembly.line.exception;

import com.neogrid.assembly.line.integrator.TxtFileIntegrator;

public class InvalidFileException extends Exception {

    public InvalidFileException() {
        super("Invalid File");
    }

    public InvalidFileException(Throwable t) {
        super("Invalid File", t);
    }
}
