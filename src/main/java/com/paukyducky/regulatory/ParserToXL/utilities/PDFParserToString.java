package com.paukyducky.regulatory.ParserToXL.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import io.github.jonathanlink.PDFLayoutTextStripper;



public class PDFParserToString {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private String fileName;
    private String output;

    private void pdftoString() {

        String text = null;
        try {
            PDFParser pdfParser = new PDFParser(new RandomAccessFile(new File(fileName), "r"));
            pdfParser.parse();

            PDDocument doc = new PDDocument(pdfParser.getDocument());
            PDFTextStripper stripper = new PDFLayoutTextStripper();
            text = stripper.getText(doc);

            doc.close();
            output = text;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("Error Parsing PDF to String: " + e);
        }
    }

    public String getOutput() {
        pdftoString();
        return output;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }




}
