/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adapter.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Admin
 */
public class SDLParser {

    String blank = "";
    public String batchNoHeader = "";
    public String sourceSystemHeader = "";
    public String balnceheader = "";
    public String ErrorLog = "";
    public String loadTypeHeader = "";
    public String resultHeader = "";

    public String getResultHeader() {
        return resultHeader;
    }

    public void setResultHeader(String resultHeader) {
        this.resultHeader = resultHeader;
    }
    
    public String getLoadTypeHeader() {
        return loadTypeHeader;
    }

    public void setLoadTypeHeader(String loadTypeHeader) {
        this.loadTypeHeader = loadTypeHeader;
    }
    
    public String getBalnceheader() {
        return balnceheader;
    }

    public void setBalnceheader(String balnceheader) {
        this.balnceheader = balnceheader;
    }

    public String getBatchNoHeader() {
        return batchNoHeader;
    }

    public void setBatchNoHeader(String batchNoHeader) {
        this.batchNoHeader = batchNoHeader;
    }

    public String getSourceSystemHeader() {
        return sourceSystemHeader;
    }

    public void setSourceSystemHeader(String sourceSystemHeader) {
        this.sourceSystemHeader = sourceSystemHeader;
    }

    public String getErrorLog() {
        return ErrorLog;
    }

    public void setErrorLog(String ErrorLog) {
        this.ErrorLog = ErrorLog;
    }

    public SDLParser(File f) throws FileNotFoundException, IOException {

        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(f));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();



        String ss[] = fileData.toString().split("\n");


        for (String s : ss) {

            String aa[] = s.split("=");
            if (aa.length > 1) {


                if ("akritiv__Load_Type__c".contains(aa[ 1].trim())) {

                    //System.out.println("Btatch header = " + aa [ 0 ].replace("\\", blank ));
                    setLoadTypeHeader(aa[ 0].replace("\\", blank));
                } else if ("akritiv__Result__c".contains(aa[ 1].trim())) {

                    //System.out.println("balance header = " + aa [ 0 ].replace("\\", blank ));
                    setBalnceheader(aa[ 0].replace("\\", blank));
                }
            }
        }

    }
}
