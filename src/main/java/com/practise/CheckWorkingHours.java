package com.practise;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;

import com.practise.helper.Shift;

public class CheckWorkingHours {
    
    public static boolean hasInsufficientRest(List<Shift> shifts) {
        // Filter out any null shifts or shifts with null start or end times
        List<Shift> validShifts = shifts.stream()
                .filter(Objects::nonNull)
                .filter(s -> s.getStartDateWithTime() != null && s.getEndDateWithTime() != null)
                .sorted(Comparator.comparing(Shift::getStartDateWithTime))
                .collect(Collectors.toList());

       for (int i = 0; i < validShifts.size() - 1; i++) {
        LocalDateTime endOfCurrentShift = validShifts.get(i).getEndDateWithTime();
        LocalDateTime startOfNextShift = validShifts.get(i + 1).getStartDateWithTime();

        Duration restDuration = Duration.between(endOfCurrentShift, startOfNextShift);
        long hoursBetween = restDuration.toHours();
        long minutesPart = restDuration.minusHours(hoursBetween).toMinutes();

        if ((hoursBetween == 1 && minutesPart > 0) || (hoursBetween > 1 && hoursBetween < 10)) {
            return true;
        }
    }

        // All shifts have sufficient rest time between them or are invalid
        return false;

    } 

    public static boolean checkForLongShifts(List<Shift> shifts) {
    
        // This map will hold the total hours worked for each day for each employee
        Map<String, Map<LocalDate, Double>> employeeDailyHours = new HashMap<>();

        // Iterate over the shifts list once
        for (Shift shift : shifts) {
            if (shift == null || shift.getStartDateWithTime() == null || shift.getEndDateWithTime() == null) {
                continue; // Skip if the shift or its time is null
            }

            String positionId = shift.getPositionId();
            LocalDate shiftDate = shift.getStartDate();
            LocalDateTime startDateTime = shift.getStartDateWithTime();
            LocalDateTime endDateTime = shift.getEndDateWithTime();

            // Calculate the duration of the current shift in hours
            double hours = Duration.between(startDateTime, endDateTime).toHours();

            // Update the daily hours map
            employeeDailyHours.computeIfAbsent(positionId, k -> new HashMap<>());
            employeeDailyHours.get(positionId).merge(shiftDate, hours, Double::sum);

            // Check if the total hours worked on this day exceeds 14 hours
            if (employeeDailyHours.get(positionId).get(shiftDate) > 14) {
                System.out.println("Employee with Position ID " + positionId + " has worked more than 14 hours on " + shiftDate);
                return true;
            }
        }
        return false;
    }


    

    

}
