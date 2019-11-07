package com.hguoli.print;

import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {

//        String csv = "C:\\Users\\user\\Desktop\\histable.csv";
//        String pdf = "C:\\Users\\user\\Desktop\\test.pdf";
        String csv = "/users/ems/histable.csv";
        String pdf = "/users/ems/test.pdf";

        List<String[]> list = CSVUtil.INSTANCE.readAll(csv);

        if (null != list && !list.isEmpty()) {
            PdfUtil.INSTANCE.createPdf(list, Arrays.asList(0, 1, 2, 3, 4), pdf);
            PrintUtil.INSTANCE.print2(pdf);
        }
    }
}
