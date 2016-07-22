/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adapter.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author dell
 */
public class AkritivBean {

    static Logger logger = Logger.getLogger(AkritivBean.class);
    private String name;
    private String notificationEmail;
    private String password;
    private String notificationSubscribers;
    private String processSequence;
    private String organizationName;
    private String mailTransportProtocol;
    private String mailHost;
    private String  mailSmtpAuth;
    private String mailSmtpPort;
    private String mailSmtpSocketFactoryPort;
    private String mailSmtpSocketFactoryClass;
    private String mailSmtpSocketFactoryFallback;
    private String mailSmtpQuitwait;
    private String mailSmtpStartTLS;
    private String subject;
    private File[] attachements;
    private Exception exception;
    private Load process;
    private Date startTime;
    private Date endTime;
    private String sdlLogFileName;
    private String sdlOutFileName;
    private String batchInfoProcessName;
    private String loadTypeHeader;
    private String resultHeader;
    private int errorLines ;
    private String useAkritivSmtp;
    private String myEmail;
    private String myPassword;
    private String useSSL;
    //Added by Nitin Munjani::03 Oct 2012::Start
    private String rawdatadir;
    private String datadir;
    private char separator;
    private char quotechar;
    private char escapechar;
    private boolean callConverter;
    private String ignoreErrorsUpto;
    private String notifySucessfullLoad;
    private String dataLoadBatchFileStarts;
    private String generateBatchFile;
    private String batchRunAging;
    private String batchLoadType;
    private String batchSourceRefColumn;
    private String batchNumberRefColumn;
    private String batchFileName;
    private String formateDataFile;
    private String rawDataFileSeparator;
    private String rawDataQuoteChar;
    private String rawDataEscapeChar;
    private int dataFileCounter = 0;
    private String siebelAccountFileKeyword;
    private String contactFileKeyword;
    private String siebelContactFileKeyword;
    private String dataLoadBatchFileKeyword;
    private String downloadDataFilesFromFTP;
    private String ftpServerName;
    private String ftpUserName;
    private String ftpPassword;
    private String ftpFileName;
    private String ftpDir;
    private String extractFromCompressedFiles;
    private String compressedFileExtention;
    private String accountsFileKeyword;
    private String transactionsFileKeyword;
    private String downloadedDataFilesLocation;
    private String dataLoaderDataDir;
    private String deleteDataFilesOnCompletion;
    private String autoRunDataLoad;
    
    public String getSiebelAccountFileKeyword() {
        return siebelAccountFileKeyword;
    }

    public void setSiebelAccountFileKeyword(String siebelAccountFileKeyword) {
        this.siebelAccountFileKeyword = siebelAccountFileKeyword;
    }

    public String getContactFileKeyword() {
        return contactFileKeyword;
    }

    public void setContactFileKeyword(String contactFileKeyword) {
        this.contactFileKeyword = contactFileKeyword;
    }

    public String getSiebelContactFileKeyword() {
        return siebelContactFileKeyword;
    }

    public void setSiebelContactFileKeyword(String siebelContactFileKeyword) {
        this.siebelContactFileKeyword = siebelContactFileKeyword;
    }

    public String getDataLoadBatchFileKeyword() {
        return dataLoadBatchFileKeyword;
    }

    public void setDataLoadBatchFileKeyword(String dataLoadBatchFileKeyword) {
        this.dataLoadBatchFileKeyword = dataLoadBatchFileKeyword;
    }

    public String getMailSmtpStartTLS() {
        return mailSmtpStartTLS;
    }

    public void setMailSmtpStartTLS(String mailSmtpStartTLS) {
        this.mailSmtpStartTLS = mailSmtpStartTLS;
    }
    
    public int getDataFileCounter() {
        return dataFileCounter;
    }

    public void setDataFileCounter(int dataFileCounter) {
        this.dataFileCounter = dataFileCounter;
    }

    public String getRawDataEscapeChar() {
        return rawDataEscapeChar;
    }

    public void setRawDataEscapeChar(String rawDataEscapeChar) {
        this.rawDataEscapeChar = rawDataEscapeChar;
    }

    public String getRawDataQuoteChar() {
        return rawDataQuoteChar;
    }

    public void setRawDataQuoteChar(String rawDataQuoteChar) {
        this.rawDataQuoteChar = rawDataQuoteChar;
    }
    public String getRawDataFileSeparator() {
        return rawDataFileSeparator;
    }

    public void setRawDataFileSeparator(String rawDataFileSeparator) {
        this.rawDataFileSeparator = rawDataFileSeparator;
    }    

    public String getAutoRunDataLoad() {
        return autoRunDataLoad;
    }

    public void setAutoRunDataLoad(String autoRunDataLoad) {
        this.autoRunDataLoad = autoRunDataLoad;
    }

    public String getDeleteDataFilesOnCompletion() {
        return deleteDataFilesOnCompletion;
    }

    public void setDeleteDataFilesOnCompletion(String deleteDataFilesOnCompletion) {
        this.deleteDataFilesOnCompletion = deleteDataFilesOnCompletion;
    }

    public String getDataLoaderDataDir() {
        return dataLoaderDataDir;
    }

    public void setDataLoaderDataDir(String dataLoaderDataDir) {
        this.dataLoaderDataDir = dataLoaderDataDir;
    }

    public String getDownloadedDataFilesLocation() {
        return downloadedDataFilesLocation;
    }

    public void setDownloadedDataFilesLocation(String downloadedDataFilesLocation) {
        this.downloadedDataFilesLocation = downloadedDataFilesLocation;
    }
    
    public String getAccountsFileKeyword() {
        return accountsFileKeyword;
    }

    public void setAccountsFileKeyword(String accountsFileKeyword) {
        this.accountsFileKeyword = accountsFileKeyword;
    }

    public String getTransactionsFileKeyword() {
        return transactionsFileKeyword;
    }

    public void setTransactionsFileKeyword(String transactionsFileKeyword) {
        this.transactionsFileKeyword = transactionsFileKeyword;
    }             

    public String getCompressedFileExtention() {
        return compressedFileExtention;
    }

    public void setCompressedFileExtention(String compressedFileExtention) {
        this.compressedFileExtention = compressedFileExtention;
    }

    public String getExtractFromCompressedFiles() {
        return extractFromCompressedFiles;
    }

    public void setExtractFromCompressedFiles(String extractFromCompressedFiles) {
        this.extractFromCompressedFiles = extractFromCompressedFiles;
    }   
   
    public String getDownloadDataFilesFromFTP() {
        return downloadDataFilesFromFTP;
    }

    public void setDownloadDataFilesFromFTP(String downloadDataFilesFromFTP) {
        this.downloadDataFilesFromFTP = downloadDataFilesFromFTP;
    }   

    public String getFtpDir() {
        return ftpDir;
    }

    public void setFtpDir(String ftpDir) {
        this.ftpDir = ftpDir;
    }

    public String getFtpFileName() {
        return ftpFileName;
    }

    public void setFtpFileName(String ftpFileName) {
        this.ftpFileName = ftpFileName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpServerName() {
        return ftpServerName;
    }

    public void setFtpServerName(String ftpServerName) {
        this.ftpServerName = ftpServerName;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }   

    public String getFormateDataFile() {
        return formateDataFile;
    }

    public void setFormateDataFile(String formateDataFile) {
        this.formateDataFile = formateDataFile;
    }

    public String getBatchFileName() {
        return batchFileName;
    }

    public void setBatchFileName(String batchFileName) {
        this.batchFileName = batchFileName;
    }
    
    public String getDataLoadBatchFileStarts() {
        return dataLoadBatchFileStarts;
    }

    public void setDataLoadBatchFileStarts(String dataLoadBatchFileStarts) {
        this.dataLoadBatchFileStarts = dataLoadBatchFileStarts;
    }

    public String getBatchLoadType() {
        return batchLoadType;
    }

    public void setBatchLoadType(String batchLoadType) {
        this.batchLoadType = batchLoadType;
    }

    public String getBatchNumberRefColumn() {
        return batchNumberRefColumn;
    }

    public void setBatchNumberRefColumn(String batchNumberRefColumn) {
        this.batchNumberRefColumn = batchNumberRefColumn;
    }

    public String getBatchRunAging() {
        return batchRunAging;
    }

    public void setBatchRunAging(String batchRunAging) {
        this.batchRunAging = batchRunAging;
    }

    public String getBatchSourceRefColumn() {
        return batchSourceRefColumn;
    }

    public void setBatchSourceRefColumn(String batchSourceRefColumn) {
        this.batchSourceRefColumn = batchSourceRefColumn;
    }

    public String getGenerateBatchFile() {
        return generateBatchFile;
    }

    public void setGenerateBatchFile(String generateBatchFile) {
        this.generateBatchFile = generateBatchFile;
    }       

    public String getNotifySucessfullLoad() {
        return notifySucessfullLoad;
    }

    public void setNotifySucessfullLoad(String notifySucessfullLoad) {
        this.notifySucessfullLoad = notifySucessfullLoad;
    }
    
    

    public String getIgnoreErrorsUpto() {
        return ignoreErrorsUpto;
    }

    public void setIgnoreErrorsUpto(String ignoreErrorsUpto) {
        this.ignoreErrorsUpto = ignoreErrorsUpto;
    }
    
    //End
        
    public String getUseSSL() {
        return useSSL;
    }

    public void setUseSSL(String useSSL) {
        this.useSSL = useSSL;
    }
    
    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public String getUseAkritivSmtp() {
        return useAkritivSmtp;
    }

    public void setUseAkritivSmtp(String useAkritivSmtp) {
        this.useAkritivSmtp = useAkritivSmtp;
    }        

    public int getErrorLines() {
        return errorLines;
    }

    public void setErrorLines(int errorLines) {
        this.errorLines = errorLines;
    }
          
    public String getResultHeader() {
        return resultHeader;
    }

    public void setResultHeader(String resultHeader) {
        this.resultHeader = resultHeader;
    }

    
    public String getLoadTypeHeader() {
        return loadTypeHeader;
    }

    public void setLoadTypeHeader(String loadTypeHeaderName) {
        this.loadTypeHeader = loadTypeHeaderName;
    }

    

    public String getBatchInfoProcessName() {
        return batchInfoProcessName;
    }

    public void setBatchInfoProcessName(String batchInfoProcessName) {
        this.batchInfoProcessName = batchInfoProcessName;
    }
    
    //Added by Nitin Munjani::03 Oct 2012::Start
    public String getRawdatadir() {
        return rawdatadir;
    }
    public void setRawdatadir(String Rawdatadir) {
        this.rawdatadir = Rawdatadir;
    }
    public String getDatadir() {
        return datadir;
    }
    public void setDatadir(String Datadir) {
        this.datadir = Datadir;
    }
    public char getSeparator() {
        return separator;
    }
    public void setSeparator(char separator) {
        this.separator = separator;
    }
    public char getQuotechar() {
        return quotechar;
    }
    public void setQuotechar(char quotechar) {
        this.quotechar = quotechar;
    }
    public char getEscapechar() {
        return escapechar;
    }
    public void setEscapechar(char escapechar) {
        this.escapechar = escapechar;
    }
    public boolean getCallConverter() {
        return callConverter;
    }
    public void setCallConverter(boolean callConverter) {
        this.callConverter = callConverter;
    }
    //End

    private List < String > successFullyprocessed = new ArrayList< String >();
    private List < String > processedWithError = new ArrayList< String >();
    private List < String > Notprocessed = new ArrayList< String >();

    public String getSdlLogFileName() {
        return sdlLogFileName;
    }

    public void setSdlLogFileName(String sdlLogFileName) {
        this.sdlLogFileName = sdlLogFileName;
    }

    public String getSdlOutFileName() {
        return sdlOutFileName;
    }

    public void setSdlOutFileName(String sdlOutFileName) {
        this.sdlOutFileName = sdlOutFileName;
    }


    public List<String> getNotprocessed() {
        return Notprocessed;
    }

    public void setNotprocessed(List<String> Notprocessed) {
        this.Notprocessed = Notprocessed;
    }

    public List<String> getProcessedWithError() {
        return processedWithError;
    }

    public void setProcessedWithError(List<String> processedWithError) {
        this.processedWithError = processedWithError;
    }

    public List<String> getSuccessFullyprocessed() {
        return successFullyprocessed;
    }

    public void setSuccessFullyprocessed(List<String> successFullyprocessed) {
        this.successFullyprocessed = successFullyprocessed;
    }

    
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    

    public String getProcessSequence() {
        return processSequence;
    }

    public void setProcessSequence(String processSequence) {
        this.processSequence = processSequence;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    public String getNotificationSubscribers() {
        return notificationSubscribers;
    }

    public void setNotificationSubscribers(String notificationSubscribers) {
        this.notificationSubscribers = notificationSubscribers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        AkritivBean.logger = logger;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    

    public String getMailSmtpPort() {
        return mailSmtpPort;
    }

    public void setMailSmtpPort(String mailSmtpPort) {
        this.mailSmtpPort = mailSmtpPort;
    }

   

    public String getMailSmtpSocketFactoryClass() {
        return mailSmtpSocketFactoryClass;
    }

    public void setMailSmtpSocketFactoryClass(String mailSmtpSocketFactoryClass) {
        this.mailSmtpSocketFactoryClass = mailSmtpSocketFactoryClass;
    }

    

    public String getMailSmtpSocketFactoryPort() {
        return mailSmtpSocketFactoryPort;
    }

    public void setMailSmtpSocketFactoryPort(String mailSmtpSocketFactoryPort) {
        this.mailSmtpSocketFactoryPort = mailSmtpSocketFactoryPort;
    }

    public String getMailTransportProtocol() {
        return mailTransportProtocol;
    }

    public void setMailTransportProtocol(String mailTransportProtocol) {
        this.mailTransportProtocol = mailTransportProtocol;
    }

    public String getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public void setMailSmtpAuth(String mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }

    public String getMailSmtpQuitwait() {
        return mailSmtpQuitwait;
    }

    public void setMailSmtpQuitwait(String mailSmtpQuitwait) {
        this.mailSmtpQuitwait = mailSmtpQuitwait;
    }

    public String getMailSmtpSocketFactoryFallback() {
        return mailSmtpSocketFactoryFallback;
    }

    public void setMailSmtpSocketFactoryFallback(String mailSmtpSocketFactoryFallback) {
        this.mailSmtpSocketFactoryFallback = mailSmtpSocketFactoryFallback;
    }

    public File[] getAttachements() {
        return attachements;
    }

    public void setAttachements(File[] attachements) {
        this.attachements = attachements;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Load getProcess() {
        return process;
    }

    public void setProcess(Load process) {
        this.process = process;
    }
    
    


}
