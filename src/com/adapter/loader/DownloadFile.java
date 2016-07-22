package com.adapter.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import com.salesforce.dataloader.process.ProcessConfig;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.apache.log4j.Logger;

public class DownloadFile {
	private AkritivBean akritivbean;
	private XmlBeanFactory beanfactory;
	static Logger logger = Logger.getLogger(AkritivDataLoad.class);

	public void download()
                throws SocketException, IOException {

		this.beanfactory = ProcessConfig.getBeanFactory();
		this.akritivbean = (AkritivBean) this.beanfactory.getBean("Akritiv");
                String strDataPath = akritivbean.getDataLoaderDataDir();// "data//";
		System.out.println("***** Downloading Files *****\n");
		FTPClient client = new FTPClient();
		FileOutputStream fos = null;

		client.connect(akritivbean.getFtpServerName());
		client.login(akritivbean.getFtpUserName(), akritivbean.getFtpPassword());

		FTPFile[] files = client.listFiles(akritivbean.getFtpDir());

		String fileStorePath = akritivbean.getDownloadedDataFilesLocation();
                new File(fileStorePath.replace("/", "").replace("//", "")).delete();
                new File(strDataPath.replace("//", "")).delete();
                new File(fileStorePath).mkdir();
                new File(strDataPath).mkdir();

                for (int i = 0; i < files.length; i++) {                                        
                    fos = new FileOutputStream(fileStorePath + files[i].getName());

                    System.out.println("Downloading... data file : " + files[i].getName());
                    Boolean filecomplete = false;
                    filecomplete = client.retrieveFile(this.akritivbean.getFtpDir()
                                    + "/" + files[i].getName(), fos);
                    if (!filecomplete) {
                            System.out.println("Download Failed : " + files[i].getName());
                            fos.close();
                    } else {
                            fos.close();                                            
                    }
                }
		client.disconnect();
	}	
}
