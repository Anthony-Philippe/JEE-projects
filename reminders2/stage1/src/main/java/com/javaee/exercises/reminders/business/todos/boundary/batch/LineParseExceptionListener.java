package com.javaee.exercises.reminders.business.todos.boundary.batch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.batch.api.chunk.listener.SkipReadListener;
import jakarta.batch.runtime.context.JobContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Dependent
@Named("LineParseExceptionListener")
public class LineParseExceptionListener implements SkipReadListener {
    private static final String FAILED_DIRECTORY = "failed_directory";
    private static final Logger logger = Logger.getLogger(LineParseExceptionListener.class.getName());
    @Inject
    private JobContext jobContext;

    @Override
    public void onSkipReadItem(Exception e) throws Exception {
        File failedDirectory = new
                File(jobContext.getProperties().getProperty(
                FAILED_DIRECTORY));
        if (!failedDirectory.exists()) {
            failedDirectory.mkdirs();
        }
        ToDoLineParseException parseException = (ToDoLineParseException) e;
        logger.log(Level.WARNING, "Problem parsing file line", parseException);
        try (PrintWriter failed = new PrintWriter(new BufferedWriter(new FileWriter(
                new File(failedDirectory, "failed_" + jobContext.getJobName() + "_" + jobContext.getInstanceId() + " .csv"), true)))) {
            failed.println(parseException.getLine());
        }
    }
}
