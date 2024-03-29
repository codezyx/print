package com.hguoli.print.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public enum PrintUtil {

    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintUtil.class);

    public static void main(String[] args) {
        PrintUtil.INSTANCE.print3("c:/Users/user/Desktop/test.pdf");
    }

    public void print3(String pdfFile) {
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        int count = 0;
        for (DocFlavor docFlavor : service.getSupportedDocFlavors()) {
            if (docFlavor.toString().contains("pdf")) {
                count++;
            }
        }
        if (count == 0) {
            System.out.println("PDF not supported by printer: " + service.getName());
            LOGGER.error("PDF not supported by printer: " + service.getName());
        } else {
            try {
                FileInputStream in = new FileInputStream(pdfFile);
                Doc doc = new SimpleDoc(in, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
                service.createPrintJob().print(doc, null);
            } catch (Exception e) {
                System.out.println("Error to print!" + e.getMessage());
                LOGGER.error("Error to print!", e);
            }
        }
    }

    public void linuxCommandPrint(String pdfFile) {
        String[] cmd = {"sh", "-c", "lpr -P HL-1208 /users/ems/test.pdf"};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            LOGGER.error("Error to execute lpr command!", e);
        }
    }

    public void print(String pdfFile) {
        FileInputStream psStream = null;
        try {
            psStream = new FileInputStream(pdfFile);
        } catch (FileNotFoundException ffne) {
            ffne.printStackTrace();
        }
        if (psStream == null) {
            return;
        }
        DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc doc = new SimpleDoc(psStream, docFlavor, null);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(docFlavor, printRequestAttributeSet);

        PrintService printService = null;
        if (services != null && services.length > 0) {
            printService = services[0];
            LOGGER.info(printService.getName());
        }

        if (printService != null) {
            DocPrintJob job = printService.createPrintJob();
            try {
                job.print(doc, printRequestAttributeSet);
            } catch (Exception e) {
                LOGGER.error("Error to print pdf!", e);
            }
        } else {
            LOGGER.error("No printer service found");
        }

    }
}
