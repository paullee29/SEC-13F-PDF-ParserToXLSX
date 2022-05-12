package com.paukyducky.regulatory.ParserToXL.services;

import com.paukyducky.regulatory.ParserToXL.models.Model13F;
import com.paukyducky.regulatory.ParserToXL.utilities.PDFParserToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse13FPDFToModelService {

    private final String fileName;
    private final Map<String, Model13F> cusipHashMap = new HashMap<>();
    private final String cusipPattern = "[a-zA-Z0-9]{1}[0-9]{1}[a-zA-Z0-9]"
            + "{4}\\s\\s\\s\\s[a-zA-Z0-9]{2}\\s\\s\\s[0-9]";

    private final Logger logger = LogManager.getLogger(this.getClass().getName());

	public Parse13FPDFToModelService(String fileName) {
		logger.info("Starting: " + this.getClass().getName());
		this.fileName = fileName;
		parse13FPDFtoModel();
	}

    public void parse13FPDFtoModel() {

        // Instantiates the PDFParser to String
        PDFParserToString pdfParser = new PDFParserToString();
        pdfParser.setFileName(fileName + ".pdf");

		//Gets the output from the PDF Stripper
        String output = pdfParser.getOutput();

        Scanner sc = new Scanner(output);
        // [a-zA-Z0-9]{1}[0-9]{1}[a-zA-Z0-9]{4}\\s\\s\\s\\s[a-zA-Z0-9]{2}\\s\\s\\s[0-9]
        // Regex Matching


        Pattern pattern = Pattern.compile(cusipPattern);
        Matcher matcher = pattern.matcher(output);


        int count = 0;

        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            matcher = pattern.matcher(line);
            if (matcher.find()) {

                String cusip = (line.substring(18, 35).replaceAll("\\s", "")).trim();
                String issuerName = (line.substring(41, 87).replaceAll("\\s\\s+", " ")).trim();
                String issuerDescription = (line.substring(87, 110).replaceAll("\\s\\s+", " ")).trim();
                String status = (line.substring(116)).trim();
                count++;

                cusipHashMap.put(cusip, new Model13F(cusip, issuerName, issuerDescription, status));


            }

        }

        logger.info("CUSIP Count: " + count);
        sc.close();
    }


    public Map<String, Model13F> getCUSIPHashMap() {
        return cusipHashMap;
    }



}
