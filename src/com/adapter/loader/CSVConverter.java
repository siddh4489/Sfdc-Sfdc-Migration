package com.adapter.loader;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.akritiv.loader.notification.EmailNotification;


public class CSVConverter {

	public CSVConverter() {
		// TODO Auto-generated constructor stub
	}
	public static void ConvertIt( AkritivBean akritivLoader) {
		try {		
                        char chSeparator = '\u0000';
                        char chQuotechar = '\u0000';
                        char chEscape  = '\u0000';

                        if(akritivLoader.getRawDataFileSeparator() != null && akritivLoader.getRawDataFileSeparator().trim().length() >0) {
                            chSeparator = akritivLoader.getRawDataFileSeparator().trim().charAt(0);
                        }
                        if(akritivLoader.getRawDataQuoteChar() != null && akritivLoader.getRawDataQuoteChar().trim().length() > 0) {
                            chQuotechar = akritivLoader.getRawDataQuoteChar().trim().charAt(0);
                        }
                        if(akritivLoader.getRawDataEscapeChar() != null && akritivLoader.getRawDataEscapeChar().trim().length() > 0) {
                            chEscape = akritivLoader.getRawDataEscapeChar().trim().charAt(0);
                        }
                        
                        String strDataPath = akritivLoader.getDataLoaderDataDir(); //"data//";
                        String strFileName;

                        File dirRawData = new File(strDataPath);
                        File[] listOfFiles = dirRawData.listFiles();

                        CSVReader reader;
                        CSVWriter writer;

                        for (int i = 0; i < listOfFiles.length; i++) {
                                if (listOfFiles[i].isFile()) {
                                        strFileName = listOfFiles[i].getName();

                                        reader = new CSVReader(new FileReader(new File(System
                                                        .getProperty("user.dir"), strDataPath + strFileName)),
                                                        chSeparator, chQuotechar, chEscape);
                                        List listrawData = reader.readAll();

                                        strFileName = strFileName.substring(0, strFileName.lastIndexOf('.') + 1) + "csv";
                                        writer = new CSVWriter(new FileWriter(new File(System
                                                        .getProperty("user.dir"), strDataPath + strFileName)),',', '\"');
                                        writer.writeAll(listrawData);
                                        writer.close();
                                        reader.close();
                                }
			}
		} catch (IOException ex) {
			System.out.print("***** Convert data files to standard csv exception*****\n");
                        EmailNotification.sendEmail(akritivLoader, ex);
                        return;
		}
	}
}
