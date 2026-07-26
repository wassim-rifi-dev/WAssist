package dev.wassim.wassist.common.exceptions;

public class PathOutsideWorkspaceException extends RuntimeException {
    public PathOutsideWorkspaceException(String message) {
        super(message);
    }
}
