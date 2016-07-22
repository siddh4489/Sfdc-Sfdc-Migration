/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adapter.loader;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.salesforce.dataloader.controller.Controller;
import com.salesforce.dataloader.process.ProcessConfig;
import com.salesforce.dataloader.process.ProcessRunner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanFactory;


/**
 *
 * @author dell
 */
public class Load {

    static Logger logger = Logger.getLogger(Load.class);
    private Controller controller ;
    private static AkritivBean bean;
    private XmlBeanFactory factory;
    public static int totalErrorCount=0;

    public XmlBeanFactory getFactory() {
        return factory;
    }

    public void setFactory(XmlBeanFactory factory) {
        this.factory = factory;
    }

    

    public static AkritivBean getBean() {
        return bean;
    }

    public static void setBean(AkritivBean bean) {
        Load.bean = bean;
    }

    public Load() {
    }

    public Load(String processName  ) {
        this.processName = processName;
        
    }
    public Load(String processName, String file) {
        this.processName = processName;
        this.file = file;

    }
    private String processName;
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public void execute()  {

        if (!validateParams()) {
            RuntimeException be = new RuntimeException("process.name not provided.");
            throw be;
        }

        if ( isBatchInfoLoad()  ){
            preBatchInfoLoad();
        }
        //run dataloader process
        Map processparams = new HashMap();
        processparams.put("process.name", processName);
        if(file != null && file.length()>0){
            processparams.put("dataAccess.name", System.getProperty("user.dir") + File.separator + bean.getDataLoaderDataDir() + file);
            processparams.put("dataFile.name", file);
        }
        try {

            ProcessRunner processRunner = ProcessRunner.getInstance(processparams);
            processRunner.run();
            controller = processRunner.getController();
            

        } catch (Exception ex) {

        	ex.printStackTrace();
            logger.error(ex.getMessage());
        }

    }
    private boolean isBatchInfoLoad() {

        return processName.equals( bean.getBatchInfoProcessName());
    }

    private void preBatchInfoLoad(){

        //get csv file
        ProcessRunner runner = ( ProcessRunner )factory.getBean( processName );
        Map < String , String > map = runner.getConfigOverrideMap();
        File  csvfile;
        if(bean.getDataLoadBatchFileKeyword() != null && bean.getDataLoadBatchFileKeyword().trim().length() > 0){
            csvfile= new File(System.getProperty("user.dir"), "data/" + this.file);
        }
        else{
            csvfile = new File(System.getProperty("user.dir"), map.get("dataAccess.name"));
        }
        //File  csvfile = new File(System.getProperty("user.dir"), "data" + File.separator + getBean().getBatchFileName());

        //get column header
        String resultHeader = bean.getResultHeader();
        String loadTypeHeader =  bean.getLoadTypeHeader();

        int resultIndex = 0 ;
        int loadTypeIndex = 0;
        try {
            //read data
            CSVReader reader = new CSVReader(new FileReader(csvfile));
            
            List<String[]> records = reader.readAll();
            if( records == null || records.size() != 2) {

                throw new RuntimeException("Batch Information is not correct");
            }
            String [] headers = records.get(0);

            int i = 0;

            for ( String s : headers ) {

                if ( s != null ) s = s.trim();
                if ( resultHeader.equals( s )) {

                    resultIndex = i;
                } else if (loadTypeHeader.equals( s )) {

                    loadTypeIndex = i;
                }

                i++;
            }

            String [] values = records.get(1);
            String ignoreErrorsUpto = bean.getIgnoreErrorsUpto();
            int ignoreErrorCount;
            if(ignoreErrorsUpto == null) {
                ignoreErrorCount = 0;
            } else {
                ignoreErrorCount = Integer.parseInt(ignoreErrorsUpto);
            }


            if ( totalErrorCount > ignoreErrorCount  ) {

                //this is error
                values[ resultIndex ] = "ERROR";
                values[ loadTypeIndex ] = values[ loadTypeIndex ]+"/ERROR";
                
            } else {

                //no error
                values[ resultIndex ] = "SUCCESS";
                //values[ loadTypeIndex ] = "FULL";
                
            }

            reader.close();

            FileWriter fw = new FileWriter(csvfile);

            CSVWriter csvwriter = new CSVWriter(fw, ',');
            csvwriter.writeAll(records);
            csvwriter.flush();
            csvwriter.close();


        } catch (Exception ex) {
            logger.error("Error While Creting Batch Info", ex);
        }

        

    }
    public boolean isError() {

      try {
        File errorfile = getErrorFile();
        if ( errorfile == null ) {

            return true;
        }
        CSVReader reader = new CSVReader(new FileReader(errorfile));

        List < String [] > results = reader.readAll();

          if (results == null) {
              return false;
          } else {
            totalErrorCount += results.size() - 1 ;
          }
          if (results.size() > 1) {
              return true;
          }
          return false;
      }catch( Exception e ) {

          return false;
      }
        
    }

    private boolean validateParams() {

        return processName != null;
    }


    public static File getLogFile () {

        return  new File(System.getProperty("java.io.tmpdir") + File.separator + bean.getSdlLogFileName());
    }

    public static File getStdOutFile () {

        return  new File(System.getProperty("java.io.tmpdir") + File.separator + bean.getSdlOutFileName());
    }


    public  File getErrorFile () {

        if (controller!= null && controller.getConfig() != null )
        return  new File( controller.getConfig().getString("process.outputError"));
        return  null;
    }

    public  File getSuccessFile () {
        if (controller!= null && controller.getConfig() != null )
        return  new File( controller.getConfig().getString("process.outputSuccess"));
        return  null;
    }


    
    public static void main(String[] args) {

        System.setProperty("salesforce.config.dir", "conf");
        //get factory

        Load load = new Load("LoadDataLoadBatch");
        load.setFactory(ProcessConfig.getBeanFactory() );
        load.setBean(( AkritivBean )load.getFactory().getBean("Akritiv"));
        List < String > s = new ArrayList < String>();
        s.add("ERROR")                ;
        load.getBean().setProcessedWithError( s );

        load.preBatchInfoLoad();


    }

    
}
