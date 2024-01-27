package com.practise;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import com.practise.helper.Shift;

public class TimecardAnalysis {

    public static void main(String[] args) {
        try {
            // Assuming the first argument is the path to the Excel file 
            String excelFilePath = "./Assignment_Timecard.xlsx";
            analyzeTimecards(excelFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void analyzeTimecards(String filePath) throws IOException, InvalidFormatException {
        FileInputStream excelFile = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        
        // Define data structures to hold employee shifts
        Map<String, List<Shift>> employeeShifts = new HashMap<>();
        
        DataFormatter formatter = new DataFormatter();
        int count =0;
        // Iterate through the rows of the sheet
        for (Row row : sheet) {
            count++;
            if (row.getRowNum() == 0) continue; // Skip header row
            try{

            // Extract information from the row
        //    System.out.println(count);
            String positionId = formatter.formatCellValue(row.getCell(0)).trim();
            String positionStatus = formatter.formatCellValue(row.getCell(1)).trim();
            String timeIn = formatter.formatCellValue(row.getCell(2)).trim();
            String timeOut = formatter.formatCellValue(row.getCell(3)).trim();
 //         String timeCardHours = row.getCell(4).getStringCellValue().trim();
 //         String payCycleStartDate =formatter.formatCellValue(row.getCell(5)).trim();
 //         String payCycleEndDate =formatter.formatCellValue(row.getCell(6)).trim();
            String employeeName =formatter.formatCellValue(row.getCell(7)).trim();
//          String fileNumber =formatter.formatCellValue(row.getCell(8)).trim();


            if (!timeIn.isEmpty() && !timeOut.isEmpty()) {

            Shift shift = new Shift(timeIn, timeOut, positionId, employeeName, positionStatus );
        List<Shift> shifts = employeeShifts.computeIfAbsent(positionId, k -> new ArrayList<>());
        shifts.add(shift);
        


    }
/*             System.out.println(positionId + " " + positionStatus + " " + timeIn + " " + timeOut + " " +
            timeCardHours + " " + payCycleStartDate + " " + payCycleEndDate + " " +
            employeeName + " " + fileNumber); */
        } catch (Exception e) {
            System.out.println("Error processing row " + count + ": " + e.getMessage());
        }

        }

      //  System.out.println("count "+count);
        workbook.close();
        System.out.println("employeeShifts size"+employeeShifts.size());

        List<Shift> printDataOfConsecutiveDays = new ArrayList<>();
        List<Shift> printDataOfInsufficientRest = new ArrayList<>();
        List<Shift> printDataOfLongShifts = new ArrayList<>();

        for (Map.Entry<String, List<Shift>> entry : employeeShifts.entrySet()) {
//          String employeeId = entry.getKey();
            List<Shift> shifts = entry.getValue();

             if (shifts.size() >= 7 && CheckConsecutiveDays.hasWorkedSevenConsecutiveDays(shifts)) {
                String name= shifts.get(0).getEmployeeName();
                String id =shifts.get(0).getPositionId();
                String status= shifts.get(0).getpositionStatus(); 
                Shift setdataOfConsecutiveDays = new Shift(id, name, status);
                printDataOfConsecutiveDays.add(setdataOfConsecutiveDays);    //Setting Shift type of data to print in a single place
                  
  //              System.out.println("Employee " + employeeId + " has worked 7 consecutive days.");
            } 


            // Check for insufficient rest between shifts
            if (CheckWorkingHours.hasInsufficientRest(shifts)) {

                
//               System.out.println("Employee " + shifts.get(0).getEmployeeName() +  " with ID " + employeeId + " has less than 10 hours of rest between some shifts."); 

                String name= shifts.get(0).getEmployeeName();
                String id =shifts.get(0).getPositionId();
                String status= shifts.get(0).getpositionStatus(); 
                Shift setdataOfInsufficientRest = new Shift(id, name, status);
                printDataOfInsufficientRest.add(setdataOfInsufficientRest);   //Setting Shift type of data to print in a single place
            }
            
            if (CheckWorkingHours.checkForLongShifts(shifts)) {

                String name= shifts.get(0).getEmployeeName();
                String id =shifts.get(0).getPositionId();
                String status= shifts.get(0).getpositionStatus(); 
                Shift setdataOfLongShifts = new Shift(id, name, status);
                printDataOfLongShifts.add(setdataOfLongShifts);    //Setting Shift type of data to print in a single place
                
                
                
            }
            
        }

        System.out.println("\n \n These Employees who has worked for 7 consecutive days \n");
        for (Shift shift : printDataOfConsecutiveDays) {
            
            System.out.println(
                    " name id: "+shift.getPositionId()+
                    "  Status: "+shift.getpositionStatus()+
                    "  name: "+shift.getEmployeeName());
            
        }

        System.out.println("\n \n These Employees have less than 10 hours of time between shifts but greater than 1 hour \n");
        for (Shift shift : printDataOfInsufficientRest) {
            
            System.out.println(
                " name id: "+shift.getPositionId()+
                "  Status: "+shift.getpositionStatus()+
                "  name: "+shift.getEmployeeName());
            
        }

        System.out.println("\n \n These Employees have worked for 14 hours \n");
        for (Shift shift : printDataOfLongShifts) {
            
            System.out.println(
                " name id: "+shift.getPositionId()+
                "  Status: "+shift.getpositionStatus()+
                "  name: "+shift.getEmployeeName());
            
        }

         
    }

}
