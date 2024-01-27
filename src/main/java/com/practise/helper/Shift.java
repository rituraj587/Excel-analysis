package com.practise.helper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Shift {
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String positionId;
    private String employeeName;
    private String positionStaus;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.US);// 09/21/2023 03:00 pm

    public Shift(String startDateTimeStr, String endDateTimeStr, String positionId, String employeeName,String positionStatus) {
        try {

            startDateTimeStr = startDateTimeStr.toUpperCase(Locale.US);// without this it was creating exception
            endDateTimeStr = endDateTimeStr.toUpperCase(Locale.US); // converts "09/21/2023 03:00 pm" to "09/21/2023 03:00 PM"



            // Parse the start and end date times
            LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, DATE_TIME_FORMATTER);
            LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, DATE_TIME_FORMATTER);

            // Set the startDate, startTime, and endTime
            this.startDate = startDateTime.toLocalDate();
            this.startTime = startDateTime.toLocalTime();
            this.endTime = endDateTime.toLocalTime();
            this.employeeName=employeeName;
            this.positionId=positionId;
            this.positionStaus=positionStatus;

            // Print the parsed dates for debugging
/*             System.out.println("positionId: " + positionId);
            System.out.println("employeeName: " + employeeName);
            System.out.println("Parsed Start Date: " + startDate);
            System.out.println("Parsed Start Time: " + startTime);
            System.out.println("Parsed End Time: " + endTime);
            System.out.println("LocalDateTime.of()"+LocalDateTime.of(startDate, startTime)); */

        } catch (DateTimeParseException e) {
            // Handle the case where the date string is empty or in a wrong format
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    public Shift(String positionId, String employeeName,String positionStatus) {
    
        // Set the startDate, startTime, and endTime
        this.employeeName=employeeName;
        this.positionId=positionId;
        this.positionStaus=positionStatus;


}

    public String getPositionId(){
        return positionId;
    }

    public String getEmployeeName(){
        return employeeName;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    public LocalDateTime getEndDateWithTime() {
        return LocalDateTime.of(startDate, endTime);
    }

    public LocalDateTime getStartDateWithTime() {
        return LocalDateTime.of(startDate, startTime);
    }
    public long getShiftDurationInHours() {
        System.out.println("Duration: "+Duration.between(getStartDateWithTime(), getEndDateWithTime()).toHours());
        return Duration.between(getStartDateWithTime(), getEndDateWithTime()).toHours();
    
    }

    public String getpositionStatus(){
        return positionStaus;
    }

    

    


}
