package com.javaee.exercises.reminders.business.todos.boundary.batch;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ToDoFilesCheckpoint implements Serializable {
    private List<File> files = new LinkedList<>();
    private int fileIndex = 0;
    private long filePointer = 0;

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public long getFilePointer() {
        return filePointer;
    }

    public void setFilePointer(long filePointer) {
        this.filePointer = filePointer;
    }

    public File currentFile() {
        if (files.size() > fileIndex) {
            return files.get(fileIndex);
        } else {
            return null;
        }
    }

    public File nextFile() {
        filePointer = 0;
        if (files.size() > ++fileIndex) {
            return files.get(fileIndex);
        } else {
            return null;
        }
    }
}