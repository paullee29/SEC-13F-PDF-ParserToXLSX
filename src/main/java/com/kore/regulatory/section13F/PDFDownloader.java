package com.kore.regulatory.section13F;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class PDFDownloader implements Downloader {
	
	private String urlLink;
	private String fileName;
	
	public PDFDownloader () {
		System.out.println("Inside PDFDownloader constructor");
	}
	
	
	
	@Override
	public void fileDownloader () {
		System.out.println("Inside fileDownloader");
		fileName = getFileName();
		
		try {
			
			FileUtils.copyURLToFile(
					  new URL(urlLink), 
					  new File(fileName+".pdf") );
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	
	
	

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}
	

	
	
	// https://www.sec.gov/divisions/investment/13f/13flist2019q2.pdf
	public String getFileName() {
		fileName = urlLink.substring(urlLink.indexOf("13f")+4, urlLink.indexOf(".pdf"));
		return fileName;
	}


}
