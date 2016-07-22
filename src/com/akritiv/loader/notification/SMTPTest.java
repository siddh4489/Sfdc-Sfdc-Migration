/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.akritiv.loader.notification;

import com.adapter.loader.AkritivBean;
import com.salesforce.dataloader.process.ProcessConfig;
import org.springframework.beans.factory.xml.XmlBeanFactory;

/**
 *
 * @author dell
 */
public class SMTPTest {


    public static void send() {

        XmlBeanFactory beanfactory = ProcessConfig.getBeanFactory();

        //get akritiv bean
        AkritivBean akritivLoader = ( AkritivBean )beanfactory.getBean("Akritiv");

        EmailNotification.smtpTest(akritivLoader);
    }
}
