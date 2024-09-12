package dev.coms4156.project.individualproject;

import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Custom logger handler that captures the most recent log record.
 * Helpful for unit testing.
 * 
 * <p>This handler overrides the {@code publish()} method to store the latest
 * {@code LogRecord} instance, allowing tests or other classes to retrieve
 * the most recent log message. It extends the {@code StreamHandler} class.
 * 
 * <p>The {@code getLatestLogRecord()} method provides access to the most
 * recent log record.
 */
public class CustomLoggerHandler extends StreamHandler {
  public Logger logger;
  private LogRecord latestLogRecord;

  @Override
  public void publish(LogRecord record) {
    latestLogRecord = record;
    super.publish(record);
  }

  @Override
  public void flush() {
    super.flush();
  }

  @Override
  public void close() throws SecurityException {
    super.close();
  }

  public LogRecord getLatestLogRecord() {
    return latestLogRecord;
  }
}
