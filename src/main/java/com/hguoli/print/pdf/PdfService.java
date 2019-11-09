package com.hguoli.print.pdf;

import com.hguoli.print.util.CSVUtil;
import com.hguoli.print.util.ConfigCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfService.class);

    public void service() {
        LOGGER.info("Reading csv data...");
        List<String[]> list = CSVUtil.INSTANCE.readAll(ConfigCache.CACHE.getCsvFilePath());
        if (null == list || list.isEmpty()) {
            LOGGER.warn("CSV data is empty!");
            return;
        }
        String pdfPath = ConfigCache.CACHE.getAppHome() + File.separator + "histable.pdf";
        LOGGER.info("Creating pdf...");
        if (PdfUtil.INSTANCE.createDoc(list, pdfPath)) {
            LOGGER.info("Opening pdf...");
            openPdf(pdfPath);
        }
    }

    private static void openPdf(String pdf) {
        String[] cmd = {"sh", "-c", "evince " + pdf + " &"};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            LOGGER.error("Error to open pdf!", e);
        }
    }
}
