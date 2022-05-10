package com.paukyducky.regulatory.ParserToXL.services;

import com.paukyducky.regulatory.ParserToXL.models.Model13F;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Map;

public class ExcelWriterService {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private String fileName;
    private Map<String, Model13F> cusipHashMap;

    public ExcelWriterService(String fileName, Map<String, Model13F> cusipHashMap) {
        logger.info("Starting: " + this.getClass().getName());
        this.fileName = fileName;
        this.cusipHashMap = cusipHashMap;
        writeToExcel();

    }

    private void writeToExcel () {
        File file = new File ("13FTemplate.xlsx");
        FileInputStream fileInputStream = null;
        XSSFWorkbook workbook = null;
        try {
            fileInputStream = new FileInputStream(file);
            // Getting the workbook instance for XLSX file
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            logger.error("Error with reading XLSX 13F Template");
        }
        
        XSSFSheet sheet = null;
        try {
            assert workbook != null;
            sheet = workbook.getSheetAt(0);
        } catch (Exception e) {
            logger.error("Error with Sheet: " + e);
        }

        Row row = sheet.createRow(0);
        Cell cusipHeaderCell = row.createCell(0);
        Cell issuerHeaderNameCell = row.createCell(1);
        Cell issuerHeaderDescriptionCell = row.createCell(2);
        Cell statusHeaderCell = row.createCell(3);

        cusipHeaderCell.setCellValue("CUSIP");
        issuerHeaderNameCell.setCellValue("Issuer Name");
        issuerHeaderDescriptionCell.setCellValue("Issuer Description");
        statusHeaderCell.setCellValue("Status");



        int currentRow = 1;
        if (cusipHashMap != null) {
            for (Map.Entry<String, Model13F> set: cusipHashMap.entrySet()) {
                Model13F model13F = set.getValue();

                Row entryRow = sheet.createRow(currentRow);
                Cell cusipCell = entryRow.createCell(0);
                Cell issuerNameCell = entryRow.createCell(1);
                Cell issuerDescriptionCell = entryRow.createCell(2);
                Cell statusCell = entryRow.createCell(3);

                cusipCell.setCellValue(model13F.getCUSIP());
                issuerNameCell.setCellValue(model13F.getIssuerName());
                issuerDescriptionCell.setCellValue(model13F.getIssuerDescription());
                statusCell.setCellValue(model13F.getStatus());

                currentRow++;

            }
        }



        //Writes the file to
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + fileName + ".xlsx";
        logger.info("Wrote file to: " + fileLocation);

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileLocation);
        } catch (FileNotFoundException e) {
            logger.error("Issue creating Excel Document at: " + fileLocation);
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);
            workbook.close();
            logger.info("Finished Excel Writer, wrote Excel file to " + fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
