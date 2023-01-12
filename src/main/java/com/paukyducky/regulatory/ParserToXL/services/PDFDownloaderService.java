package com.paukyducky.regulatory.ParserToXL.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PDFDownloaderService implements DownloaderServiceInterface {

	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	
	private String urlLink = "https://www.sec.gov/files/investment/13flist";
	private String fileName;


	public PDFDownloaderService() {
		logger.info("Starting: " + this.getClass().getName());
		generateURLLink();
		logger.info("Getting PDF from: " + urlLink);
		fileDownloader();
		logger.info("Completed Downloaded PDF to: " + fileName+".pdf");
	}

	private void fileDownloader () {

		fileName = getFileName();
		
		try {
			logger.debug("Trying to download from: " + this.getClass().getName());
			FileUtils.copyURLToFile(
					  new URL(urlLink), 
					  new File(fileName+".pdf") );
		} catch (MalformedURLException e) {
			logger.error("Error with URL: " + e);
		} catch (IOException e) {
			logger.error("Error with IOException: " + e);
		}
	}

	private void generateURLLink () {
		//https://www.sec.gov/files/investment/13flist2021q2.pdf
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year  = localDate.getYear();
		int quarter = localDate.get(IsoFields.QUARTER_OF_YEAR);
		if (quarter == 1) {
			year--;
			quarter = 4;
		} else {
			quarter-=1;
		}
		urlLink = "https://www.sec.gov/files/investment/13flist" + year + "q" + quarter + ".pdf";
		logger.info("URL Link: " + urlLink);
	}
	

	// https://www.sec.gov/divisions/investment/13f/13flist2019q2.pdf
	public String getFileName() {
		fileName = urlLink.substring(urlLink.indexOf("13f"), urlLink.indexOf(".pdf"));
		return fileName;
	}



}
