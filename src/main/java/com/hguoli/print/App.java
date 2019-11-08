package com.hguoli.print;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        try {
            ConfigCache.CACHE.load();
        } catch (Exception e) {
            LOGGER.error("Error to start App!", e);
        }

        System.out.println(ConfigCache.CACHE);
//        String csv = "C:\\Users\\user\\Desktop\\histable.csv";
//        String pdf = "C:\\Users\\user\\Desktop\\test.pdf";

//        String csv = "/users/ems/histable.csv";
//        String pdf = "/users/ems/test.pdf";
//
//        List<String[]> list = CSVUtil.INSTANCE.readAll(csv);
//
//        if (null != list && !list.isEmpty()) {
//            PdfUtil.INSTANCE.createPdf(list, Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7), pdf);
//            openPdf(pdf);
//        }
    }

    private static void openPdf(String pdf) {
        String[] cmd = {"sh", "-c", "evince /users/ems/test.pdf"};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            LOGGER.error("Error to open pdf!", e);
        }
    }
}
