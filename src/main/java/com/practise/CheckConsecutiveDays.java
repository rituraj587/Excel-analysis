package com.practise;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import java.util.Comparator;


import com.practise.helper.Shift;

public class CheckConsecutiveDays {
 // ... [rest of your methods and classes] ...

    // ... [other methods and classes] ...

    public static boolean hasWorkedSevenConsecutiveDays(List<Shift> shifts) {
        if (shifts.size() < 7) {
            return false; // If there are fewer than 7 shifts, it's not possible to have worked 7 consecutive days
        }
        

        Collections.sort(shifts, Comparator.comparing(Shift::getStartDate, Comparator.nullsFirst(Comparator.naturalOrder())));
 // Sort by start time

        int consecutiveDays = 1; // Start with 1 to count the first day
        LocalDate lastDate = shifts.get(0).getStartDate(); // Initialize with the first shift's date

        for (int i = 1; i < shifts.size(); i++) {

          //  System.out.println("list of start shifts "+shifts.get(i).getStartDate());

            LocalDate currentDate = shifts.get(i).getStartDate();
            if (lastDate.plusDays(1).equals(currentDate)) {
                consecutiveDays++; // Increase the count if the days are consecutive
                if (consecutiveDays >= 7) {
 //                   System.out.println("true");
                    return true; // Found 7 consecutive days
                }
            } else {
                consecutiveDays = (lastDate.equals(currentDate)) ? consecutiveDays : 1; // Reset if not consecutive, unless it's the same day
            }
            lastDate = currentDate; // Update the lastDate to the current date
        }

        return false; // Less than 7 consecutive days found
    }

    // ... [rest of your methods and classes] ...
}
