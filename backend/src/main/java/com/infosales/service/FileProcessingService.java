package com.infosales.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileProcessingService {

    public List<Map<String, String>> readFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty.");
        }

        if (filename == null || (!filename.toLowerCase().endsWith(".csv") && !filename.toLowerCase().endsWith(".xlsx"))) {
            throw new IllegalArgumentException("Only CSV and XLSX files are allowed.");
        }

        List<Map<String, String>> rows;

        if (filename.toLowerCase().endsWith(".csv")) {
            rows = readCsv(file);
        } else {
            rows = readExcel(file);
        }

        if (rows.isEmpty()) {
            throw new IllegalArgumentException("The uploaded file contains no data rows.");
        }

        validateRequiredColumns(rows.get(0));

        return rows;
    }

    private List<Map<String, String>> readCsv(MultipartFile file) throws Exception {
        List<Map<String, String>> rows = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(file.getInputStream()));

        for (CSVRecord record : records) {
            Map<String, String> row = new HashMap<>();
            for (String header : record.toMap().keySet()) {
                row.put(header, record.get(header));
            }
            rows.add(row);
        }

        return rows;
    }

    private List<Map<String, String>> readExcel(MultipartFile file) throws Exception {
        List<Map<String, String>> rows = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            workbook.close();
            throw new IllegalArgumentException("Excel file is missing a header row.");
        }

        List<String> headers = new ArrayList<>();

        for (Cell cell : headerRow) {
            headers.add(cell.getStringCellValue().trim());
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow == null) continue;

            Map<String, String> rowData = new HashMap<>();

            for (int j = 0; j < headers.size(); j++) {
                Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                rowData.put(headers.get(j), getCellValueAsString(cell));
            }

            rows.add(rowData);
        }

        workbook.close();
        return rows;
    }

    private void validateRequiredColumns(Map<String, String> sampleRow) {
        List<String> requiredColumns = List.of("Product_Category", "Region", "Units_Sold", "Revenue");

        for (String column : requiredColumns) {
            if (!sampleRow.containsKey(column)) {
                throw new IllegalArgumentException("Missing required column: " + column);
            }
        }
    }

    private String getCellValueAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                double value = cell.getNumericCellValue();
                if (value == (int) value) {
                    yield String.valueOf((int) value);
                }
                yield String.valueOf(value);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            case BLANK -> "";
            default -> "";
        };
    }
}