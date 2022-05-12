package com.paukyducky.regulatory.ParserToXL;



//CUSIP Pattern: [0-9]{3}[a-zA-Z0-9]{2}[a-zA-Z0-9*@#][\s][a-zA-Z0-9*@#]{2}[\s][0-9]

import com.paukyducky.regulatory.ParserToXL.models.Model13F;
import com.paukyducky.regulatory.ParserToXL.services.DownloaderServiceInterface;
import com.paukyducky.regulatory.ParserToXL.services.ExcelWriterService;
import com.paukyducky.regulatory.ParserToXL.services.PDFDownloaderService;
import com.paukyducky.regulatory.ParserToXL.services.Parse13FPDFToModelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class App {


	private static final Logger logger = LogManager.getLogger(App.class);
	public static void main(String[] args) {

		logger.info("Starting 13F App");

		//Downloader Constructor

		DownloaderServiceInterface downloader = new PDFDownloaderService();
		String fileName = downloader.getFileName();

		Parse13FPDFToModelService parse13FPDFToModelService = new Parse13FPDFToModelService(fileName);
		Map<String, Model13F> cusipHashMap = parse13FPDFToModelService.getCUSIPHashMap();

		new ExcelWriterService(fileName, cusipHashMap);


		logger.info("Finished 13F App");

	}

}
