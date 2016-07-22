/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adapter.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Admin
 */
public class ZipUnZip {

    public ZipUnZip() {
    }

    public static File zipIt(  List < File > files ) {
       
        byte buf[] = new byte[1024];

        try {

            Date currentTime = new Date();
            SimpleDateFormat format = new SimpleDateFormat("MMddyyhhmmssSSS");
            String timestamp = format.format(currentTime);
            String outFileName = System.getProperty("user.dir")+File.separator+"archive"+File.separator+"Archive"+timestamp+".zip";
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFileName));

            for ( File f : files ) {
                if(f.exists()){
                    FileInputStream fin = new FileInputStream(f );
                    out.putNextEntry(new ZipEntry(f.getName()));
                    int len;
                    while ((len = fin.read(buf)) > 0) {
                        out.write(buf, 0, len);

                    }
                    out.closeEntry();
                    fin.close();
                }
            }
            out.close();
            return new File(outFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }       
    /**
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public static void unZipIt(String zipFile,AkritivBean akritivLoader){

     byte[] buffer = new byte[300000];

     try{
        String outputFolder = akritivLoader.getDataLoaderDataDir();// "data//";
    	//create output directory is not exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}

    	//get the zip file content
    	ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();

    	while(ze!=null){

    	   String fileName = ze.getName();
           File newFile = new File(System.getProperty("user.dir"),outputFolder + fileName);

           System.out.println("file unzip : "+ newFile.getAbsoluteFile());

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
    	}

        zis.closeEntry();
    	zis.close();

    	System.out.println("Done");

    }catch(IOException ex){
       //ex.printStackTrace();
       Logger.getLogger(ZipUnZip.class.getName()).log(Level.SEVERE, null, ex);
    }
   }
    
  public static void unGzIt(String zipFileDir, String zipFile, AkritivBean akritivLoader) {
        GZIPInputStream gin = null;        
        try {
            String outputFolder = akritivLoader.getDataLoaderDataDir();// "data//";
            gin = new GZIPInputStream(new FileInputStream(zipFileDir + File.separator + zipFile));
            File outFile = new File(System.getProperty("user.dir"), outputFolder + zipFile.replaceAll("\\.gz$", ""));
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buf = new byte[300000];
            int len;
            while ((len = gin.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }
            gin.close();
            fos.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ZipUnZip.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                gin.close();
            } catch (IOException ex) {
                Logger.getLogger(ZipUnZip.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
  }
}
