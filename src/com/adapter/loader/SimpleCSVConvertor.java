/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adapter.loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.akritiv.loader.notification.EmailNotification;
/**
 *
 * @author Administrator
 */
public class SimpleCSVConvertor {
    
    public static void ConvertIt(AkritivBean akritivLoader) {
	        
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try	
                {				   	
		   String line = null;
		   String modifiedline = null;
                   
                   String strDataPath = akritivLoader.getDataLoaderDataDir();// "data//";
                   String strFileName;

                    File dirRawData = new File(strDataPath);
                    File[] listOfFiles = dirRawData.listFiles();
                   
                    for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isFile()) {
                                
                                strFileName = listOfFiles[i].getName();
                                File dataFile = new File(System.getProperty("user.dir"), strDataPath + strFileName);
                                reader = new BufferedReader(new FileReader(dataFile));
                                                                
                                File tmpFile = new File(System.getProperty("user.dir"), strDataPath + strFileName + ".tmp");
                                writer = new BufferedWriter(new FileWriter(tmpFile));
    
                                //read each line of text file
                                while((line = reader.readLine()) != null){
                                     modifiedline = line.replace("\"", "\"\"");
                                     modifiedline = "\""+modifiedline+"\"";
                                     modifiedline = modifiedline.replace("|^", "\",\"");

                                     writer.write(modifiedline);
                                     writer.newLine();
                                }                                     
                                 //close the files
                                writer.close();
                                reader.close();                                    											
                                dataFile.delete();
                                tmpFile.renameTo(dataFile);
                            }
                    }	    		                     
		    
		} catch (IOException ex) {
			System.out.print("***** Convert data files to standard csv exception*****\n");
                        EmailNotification.sendEmail(akritivLoader, ex);
                        return;
		}	
    }       
}
