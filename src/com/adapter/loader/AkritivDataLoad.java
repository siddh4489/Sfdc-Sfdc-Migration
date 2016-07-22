/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adapter.loader;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.akritiv.loader.notification.EmailNotification;
import com.akritiv.loader.notification.SMTPTest;
import com.salesforce.dataloader.process.ProcessConfig;
import com.salesforce.dataloader.process.ProcessRunner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanFactory;

/**
 *
 * @author dell
 */
public class AkritivDataLoad {

	private XmlBeanFactory beanfactory;
	private AkritivBean akritivLoader;
	private String[] processes;
	private List<Load> loads = new ArrayList<Load>();
	static Logger logger = Logger.getLogger(AkritivDataLoad.class);
        private DownloadFile Downloadfile;

        public void init(){

             //get factory
            beanfactory = ProcessConfig.getBeanFactory();

            //get akritiv bean
            akritivLoader = (AkritivBean) beanfactory.getBean("Akritiv");
            akritivLoader.setDataLoaderDataDir("data//");
            //delete sdl.log file
            if (!deleteLogFile() || !deleteStdoutFile()) {

                    Exception be = new Exception("Could Not Delete existing Log Files");
                    EmailNotification.sendEmail(akritivLoader, be);
                    return;
            }

             Load.setBean(akritivLoader);
        }
 
	public void execute() {

            loads.clear();
            //get all processes
            processes = akritivLoader.getProcessSequence().split(",");
            
            //if (akritivLoader.getDownloadDataFilesFromFTP() != null && akritivLoader.getDownloadDataFilesFromFTP().trim().equalsIgnoreCase("yes")) {
            //    downloadFiles();
            //}
            if (akritivLoader.getExtractFromCompressedFiles() != null && akritivLoader.getExtractFromCompressedFiles().trim().equalsIgnoreCase("yes")) {
                unZipfiles();
            }
            else if(akritivLoader.getDownloadedDataFilesLocation() != null && akritivLoader.getDownloadedDataFilesLocation().trim().length() > 0) {
                moveToDataLoaderDataDir();
            }

            if (akritivLoader.getFormateDataFile() != null && akritivLoader.getFormateDataFile().trim().equalsIgnoreCase("yes")) {
                CSVConverter.ConvertIt(akritivLoader);
                //SimpleCSVConvertor.ConvertIt(akritivLoader);
            }            
            if (akritivLoader.getGenerateBatchFile() != null && akritivLoader.getGenerateBatchFile().trim().equalsIgnoreCase("yes")) {
                generateDataLoadBatch(akritivLoader);
            }                        
            printProcesses();

            if (processes == null || processes.length == 0) {
                    Exception be = new Exception("Processes Are Not Defined in Akritiv Bean");
                    EmailNotification.sendEmail(akritivLoader, be);
            }
            
            String strlstFileName;
            String strFileName;

            File dirRawData = new File(akritivLoader.getDataLoaderDataDir());
            File[] listOfFiles = dirRawData.listFiles();

            /*Arrays.sort(listOfFiles, new Comparator<File>() {
                    public int compare(File f1, File f2) {
                        return Long.valueOf(f2.lastModified()).compareTo(
                                f1.lastModified());
                    }
                });*/
                
            //run process one by one
            Load.totalErrorCount = 0;
            for (String process : processes) {

                    try {
                            akritivLoader.setStartTime(new Date());
                            logger.info("*****" + process + "*****\n");                                                     
                            Load load;
                            strlstFileName="";
                            strFileName="";

                            if(akritivLoader.getAccountsFileKeyword() != null && akritivLoader.getAccountsFileKeyword().trim().length() > 0 && process.equalsIgnoreCase("loadaccounts")){
                                    for (int i = 0; i < listOfFiles.length; i++) {
                                       strlstFileName = listOfFiles[i].getName();
                                        if(strlstFileName.toLowerCase().contains(akritivLoader.getAccountsFileKeyword().toLowerCase())){
                                                strFileName = strlstFileName;
                                                break;
                                            }
                                        }
                                        load= new Load(process,strFileName);                                
                            } else if(akritivLoader.getTransactionsFileKeyword() != null && akritivLoader.getTransactionsFileKeyword().trim().length() > 0 && process.equalsIgnoreCase("loadtransactions")) {
                                for (int i = 0; i < listOfFiles.length; i++) {
                                    strlstFileName = listOfFiles[i].getName();
                                    if(strlstFileName.toLowerCase().contains(akritivLoader.getTransactionsFileKeyword().toLowerCase())){
                                            strFileName = strlstFileName;
                                            break;
                                        }
                                    }
                                    load = new Load(process,strFileName);                                                                    
                            } else if(akritivLoader.getSiebelAccountFileKeyword() != null && akritivLoader.getSiebelAccountFileKeyword().trim().length() > 0 && process.equalsIgnoreCase("loadsiebelaccounts")) {
                                for (int i = 0; i < listOfFiles.length; i++) {
                                    strlstFileName = listOfFiles[i].getName();
                                    if(strlstFileName.toLowerCase().contains(akritivLoader.getSiebelAccountFileKeyword().toLowerCase())){
                                            strFileName = strlstFileName;
                                            break;
                                        }
                                }
                                load = new Load(process,strFileName);                                                                    
                            } else if(akritivLoader.getContactFileKeyword() != null && akritivLoader.getContactFileKeyword().trim().length() > 0 && process.equalsIgnoreCase("loadcontacts")) {
                                for (int i = 0; i < listOfFiles.length; i++) {
                                    strlstFileName = listOfFiles[i].getName();
                                    if(strlstFileName.toLowerCase().contains(akritivLoader.getContactFileKeyword().toLowerCase())){
                                            strFileName = strlstFileName;
                                            break;
                                        }
                                }
                                load = new Load(process,strFileName);                                                                    
                            } else if(akritivLoader.getSiebelContactFileKeyword() != null && akritivLoader.getSiebelContactFileKeyword().trim().length() > 0 && process.equalsIgnoreCase("loadsiebelcontacts")) {
                                for (int i = 0; i < listOfFiles.length; i++) {
                                    strlstFileName = listOfFiles[i].getName();
                                    if(strlstFileName.toLowerCase().contains(akritivLoader.getSiebelContactFileKeyword().toLowerCase())){
                                            strFileName = strlstFileName;
                                            break;
                                        }
                                }
                                load = new Load(process,strFileName);                                                                    
                            } else if(akritivLoader.getDataLoadBatchFileKeyword() != null && akritivLoader.getDataLoadBatchFileKeyword().trim().length() > 0 && process.equalsIgnoreCase("loadbatchinfo")) {
                                for (int i = 0; i < listOfFiles.length; i++) {
                                    strlstFileName = listOfFiles[i].getName();
                                    if(strlstFileName.toLowerCase().contains(akritivLoader.getDataLoadBatchFileKeyword().toLowerCase())){
                                            strFileName = strlstFileName;
                                            break;
                                        }
                                }
                                load = new Load(process,strFileName);                                                                    
                            } else {
                                 load = new Load(process);
                            }
                            
                            load.setFactory(beanfactory);
                            loads.add(load);
                            load.execute();
                                                       
                            if (load.isError()) {
                                    akritivLoader.getProcessedWithError().add(process);
                                    EmailNotification.sendEmail(akritivLoader ,load , true );
                            } else {
                                    akritivLoader.getSuccessFullyprocessed().add(process);
                                    EmailNotification.sendEmail(akritivLoader ,load ,false );
                            }

                    } catch (Exception e) {

                            EmailNotification.sendEmail(akritivLoader , e );
                            logger.error("error", e);
                    }
            }
            archiveIt();
            cleanIt();      
	}
        private void generateDataLoadBatch(AkritivBean akritivLoader){
        try {                               
                String strDataPath = akritivLoader.getDataLoaderDataDir();// "data//";
                String strFileName = "DataLoadBatch.csv";
                String strBatchSource;                
                String strBatchNumber;
                String strBatchRunAging="YES";
                String strBatchType="FULL";
                String strTranFileName = "Transactions.csv";
                int intBatchSourceRefCol = Integer.valueOf(akritivLoader.getBatchSourceRefColumn().trim());
                int intBatchNumRefCol = Integer.valueOf(akritivLoader.getBatchNumberRefColumn().trim());

                String[] strFirstRow;

                File fileTran;

                File dirData = new File(strDataPath);
                File[] listOfFiles = dirData.listFiles();

                ProcessRunner runner = (ProcessRunner)beanfactory.getBean("LoadTransactions");
                Map<String, String> map = runner.getConfigOverrideMap();
                fileTran = new File(System.getProperty("user.dir"), map.get("dataAccess.name"));

                if(akritivLoader.getTransactionsFileKeyword() != null && akritivLoader.getTransactionsFileKeyword().trim().length() > 0){
                    for (int i = 0; i < listOfFiles.length; i++) {
                        if(listOfFiles[i].getName().toLowerCase().contains(akritivLoader.getTransactionsFileKeyword().toLowerCase())){
                                strTranFileName = listOfFiles[i].getName();
                                break;
                            }
                        }
                    fileTran = new File(System.getProperty("user.dir"), strDataPath + strTranFileName);
                }

                if(fileTran.exists())
                {
                    CSVReader reader;
                    reader = new CSVReader(new FileReader(fileTran), ',', '"');
                    reader.readNext();
                    strFirstRow = reader.readNext();
                    reader.close();
                    strBatchSource = strFirstRow[intBatchSourceRefCol];
                    strBatchNumber = strFirstRow[intBatchNumRefCol];

                    String[] strBatchColumns = {"BATCH NUM","SOURCE SYSTEM","LOAD TYPE","RESULT","ERROR","RUN BATCH AGING"};

                    if(akritivLoader.getBatchRunAging().trim().length() > 0) {
                        strBatchRunAging = akritivLoader.getBatchRunAging().toUpperCase();
                    }
                    if(akritivLoader.getBatchLoadType().trim().length() > 0) {
                        strBatchType = akritivLoader.getBatchLoadType().toUpperCase();
                    }                
                    String[] strBatchRow = {strBatchNumber,strBatchSource,strBatchType,"","",strBatchRunAging};

                    CSVWriter writer;
                    writer = new CSVWriter(new FileWriter(new File(System.getProperty("user.dir"),strDataPath + strFileName)), ',', '\"');
                    writer.writeNext(strBatchColumns);
                    writer.writeNext(strBatchRow);
                    writer.close();
                }
            
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(AkritivDataLoad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        private boolean hasFilesToLoad(){
            logger.info("*****Scanning data files*****\n");
            if(akritivLoader.getDownloadedDataFilesLocation() != null && akritivLoader.getDownloadedDataFilesLocation().trim().length() > 0){
                File dirData = new File(akritivLoader.getDownloadedDataFilesLocation());
                return dirData.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        try {
                            File f = new File(dir, name);
                            if (new File(dir, name).length() == 0) {
                                return false;
                            }
                            String extention = name.substring(name.lastIndexOf('.'));
                            return extention.equalsIgnoreCase(".csv");

                        } catch (Exception e) {
                            System.out.println(e);
                            return false;
                        }
                    }
                }).length >= akritivLoader.getDataFileCounter();
            }
            else{
                File dirData = new File(akritivLoader.getDataLoaderDataDir());
                return dirData.listFiles().length > 0;
            }
        }
	private List<File> getRawDataFiles() {

		List<File> files = new ArrayList<File>();
		//1 file for each process
		for (String process : processes) {

			ProcessRunner runner = (ProcessRunner) beanfactory.getBean(process);
			Map<String, String> map = runner.getConfigOverrideMap();
			File file = new File(System.getProperty("user.dir"), map
					.get("dataAccess.name"));
			files.add(file);
		}

		return files;

	}
        private void downloadFiles(){
            try
              {
                Downloadfile = new DownloadFile();
                Downloadfile.download();
		System.out.print("***** File Downloaded Successfully *****\n");
              }catch (Exception ex) {
                  System.out.print("***** File Download exception*****\n");
                  EmailNotification.sendEmail(this.akritivLoader, ex);
                  return;
              }
        }
        private void unZipfiles(){
            logger.info("*****Extracting data files*****\n");
            String strZipDataPath = akritivLoader.getDownloadedDataFilesLocation();
            String strZipFileName;

            File dirZipData = new File(strZipDataPath);
            File[] listOfZipFiles = dirZipData.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        try {
                            File f = new File(dir, name);
                            if (new File(dir, name).length() == 0) {
                                return false;
                            }
                            String extention = name.substring(name.lastIndexOf('.'));
                            return extention.equalsIgnoreCase(akritivLoader.getCompressedFileExtention().toLowerCase());

                        } catch (Exception e) {
                            System.out.println(e);
                            return false;
                        }
                    }
                });
            
                if(akritivLoader.getCompressedFileExtention().toLowerCase().contains(".zip")){
                    for (int i = 0; i < listOfZipFiles.length; i++) {
                        strZipFileName = listOfZipFiles[i].getName();
                        ZipUnZip.unZipIt( strZipDataPath + strZipFileName, akritivLoader);
                    }
                }
                else if(akritivLoader.getCompressedFileExtention().toLowerCase().contains(".gz")){
                    for (int i = 0; i < listOfZipFiles.length; i++) {
                        strZipFileName = listOfZipFiles[i].getName();
                        ZipUnZip.unGzIt( strZipDataPath, strZipFileName,akritivLoader );
                    }
                }            
        }
        private void moveToDataLoaderDataDir(){
            logger.info("*****Moving data files to dataloader data directory*****\n");           

            InputStream inStream = null;
            OutputStream outStream = null;

            try{

                File dirDownloadedData = new File(akritivLoader.getDownloadedDataFilesLocation());
                File[] listOfDataFiles = dirDownloadedData.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        try {
                            File f = new File(dir, name);
                            if (new File(dir, name).length() == 0) {
                                return false;
                            }
                            String extention = name.substring(name.length() - 4, name.length());
                            return extention.equalsIgnoreCase(".csv");

                        } catch (Exception e) {
                            System.out.println(e);
                            return false;
                        }
                    }
                });

                /*for (int i = 0; i < listOfDataFiles.length; i++) {
                   if(!listOfDataFiles[i].renameTo(new File(System.getProperty("user.dir"),akritivLoader.getDataLoaderDataDir() + listOfDataFiles[i].getName()))){
                        logger.info("File is failed to move!" + listOfDataFiles[i].getName());
                   }
                }*/

                for (int i = 0; i < listOfDataFiles.length; i++) {
                        
                        inStream = new FileInputStream(listOfDataFiles[i]);
                        outStream = new FileOutputStream(new File(System.getProperty("user.dir"),akritivLoader.getDataLoaderDataDir() + listOfDataFiles[i].getName()));

                        byte[] buffer = new byte[1024];
                        int length;
                        //copy the file content in bytes
                        while ((length = inStream.read(buffer)) > 0){
                            outStream.write(buffer, 0, length);
                        }
                        inStream.close();
                        outStream.close();
                }

            }catch(IOException e){
                  logger.error("error", e);
            }

        }
	private void archiveIt() {

		//archive files
		List<File> files = getDataFiles();
		List<File> errorFiles = getErrorFiles();
		List<File> logFiles = getLogFiles();

		files.addAll(errorFiles);
		files.addAll(logFiles);
		logger.info("*****Archiving*****\n");
		ZipUnZip.zipIt(files);
	}

	private boolean deleteLogFile() {

		File logFile = new File(System.getProperty("java.io.tmpdir")
				+ File.separator + akritivLoader.getSdlLogFileName());

		if (logFile.exists()) {

			if (logFile.delete()) {

				return true;
			} else {

				return false;
			}
		} else {

			return true;
		}
	}

	private boolean deleteStdoutFile() {

		File logFile = new File(System.getProperty("java.io.tmpdir")
				+ File.separator + akritivLoader.getSdlOutFileName());

		if (logFile.exists()) {

			if (logFile.delete()) {

				return true;
			} else {

				return false;
			}
		} else {

			return true;
		}
	}

	private boolean deleteDefaultXmlLogFile() {

		File logFile = new File(System.getProperty("user.dir") + File.separator
				+ "status" + File.separator + "default.log");

		if (logFile.exists()) {

			if (logFile.delete()) {

				return true;
			} else {

				return false;
			}
		} else {

			return true;
		}
	}

	private List<File> getDataFiles() {
            
            String strDataPath = akritivLoader.getDataLoaderDataDir();// "data//";
            String strlstFileName;
            String strFileName;
            List<File> files = new ArrayList<File>();
            File file;
            File dirRawData = new File(strDataPath);
            File[] listOfFiles = dirRawData.listFiles();

            for (String process : processes) {

                strlstFileName="";
                strFileName="";

                 if(akritivLoader.getAccountsFileKeyword() != null && process.toLowerCase().equals("loadaccounts")){
                    for (int i = 0; i < listOfFiles.length; i++) {
                       strlstFileName = listOfFiles[i].getName();
                        if(strlstFileName.toLowerCase().contains(akritivLoader.getAccountsFileKeyword().toLowerCase())){
                                strFileName = strlstFileName;
                                break;
                            }
                        }
                    file = new File(System.getProperty("user.dir"), strDataPath + strFileName);
                    }
                 else if(akritivLoader.getTransactionsFileKeyword() != null && process.toLowerCase().equals("loadtransactions")){
                    for (int i = 0; i < listOfFiles.length; i++) {
                        strlstFileName = listOfFiles[i].getName();
                        if(strlstFileName.toLowerCase().contains(akritivLoader.getTransactionsFileKeyword().toLowerCase())){
                                strFileName = strlstFileName;
                                break;
                            }
                        }
                    file = new File(System.getProperty("user.dir"), strDataPath + strFileName);
                    }
                else{
                    ProcessRunner runner = (ProcessRunner) beanfactory.getBean(process);
                    Map<String, String> map = runner.getConfigOverrideMap();
                    file = new File(System.getProperty("user.dir"), map.get("dataAccess.name"));
                }
                 files.add(file);
            }
		return files;
	}       
        
        private void deleteDowmloadedDataFiles(){
         if(akritivLoader.getDownloadedDataFilesLocation() != null && akritivLoader.getDownloadedDataFilesLocation().trim().length() > 0 ){           
            File dirData = new File(akritivLoader.getDownloadedDataFilesLocation());
            File[] listOfFiles = dirData.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                    listOfFiles[i].delete();
                }
            }
        }
        
        private void deleteDataFiles(){

            if(akritivLoader.getDeleteDataFilesOnCompletion() != null && akritivLoader.getDeleteDataFilesOnCompletion().equalsIgnoreCase("yes")){
                File dirData = new File(akritivLoader.getDataLoaderDataDir());
                File[] listOfFiles = dirData.listFiles();

                for (int i = 0; i < listOfFiles.length; i++) {
                        listOfFiles[i].delete();
                }
            }
        }

	private List<File> getErrorFiles() {

		List<File> files = new ArrayList<File>();
		//1 file for each process
		for (Load load : loads) {

			File file = load.getErrorFile();
			if (file != null)
				files.add(file);
		}

		return files;

	}

	public static void main(String[] args) {

		//set conf dir property
		System.setProperty("salesforce.config.dir", "conf");
		if (args != null) {

			if (args.length > 0) {

				if ("smtptest".equals(args[0])) {

					SMTPTest.send();
					return;
				} else {

					System.out.println("invalid options");
					return;
				}
			}
		}
                
                    AkritivDataLoad loader = new AkritivDataLoad();
                    loader.init();
                    loader.LoaderExecution();                    
	}
        private void LoaderExecution(){
            if(akritivLoader.getAutoRunDataLoad() != null && akritivLoader.getAutoRunDataLoad().trim().equalsIgnoreCase("yes")){
            try {
                while( true ){
                  if (!hasFilesToLoad()){
                      Thread.sleep(1000*60*5);
                      System.out.println("Last scan " + new Date());
                  }else{
                       System.out.println("Files found..loading ");
                       execute();
                  }
                }
            } catch (Exception e) {
                    e.printStackTrace();
            }
            }
            else{
                execute();
            }
        }

	private List<File> getLogFiles() {

		List<File> files = new ArrayList<File>();
		files.add(Load.getLogFile());
		files.add(Load.getStdOutFile());

		return files;
	}

	private void printProcesses() {

		logger.info("This session will execure following process in given sequesnce\n");
		for (String s : processes) {

			logger.info(s + "\n");
		}
	}

	private void cleanIt() {
		deleteDefaultXmlLogFile();
                deleteDowmloadedDataFiles();
                deleteDataFiles();
	}

}
