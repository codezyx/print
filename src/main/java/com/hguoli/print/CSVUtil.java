package com.hguoli.print;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CSVUtil {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVUtil.class);

    /**
     * 读取第一行
     *
     * @param csvFile String
     * @return String[]
     */
    public String[] readFirstLine(String csvFile) {
        String[] result = new String[]{};
        CSVReader csvReader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(csvFile));
            csvReader = new CSVReader(new InputStreamReader(fileInputStream, "gbk"));
            result = csvReader.readNext();
            System.out.println(Arrays.toString(result));
        } catch (Exception e) {
            LOGGER.error("Error to read CSV: " + csvFile, e);
        } finally {
            if (null != csvReader) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    LOGGER.warn("Error to close CSVReader !", e);
                }
            }
        }
        return result;
    }

    /**
     * 读取所有数据
     *
     * @param csvFile String
     * @return List
     */
    public List<String[]> readAll(String csvFile) {
        List<String[]> result = new ArrayList<>();
        CSVReader csvReader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(csvFile));
            csvReader = new CSVReader(new InputStreamReader(fileInputStream, "gbk"));
            result = csvReader.readAll();
            result.forEach(it -> System.out.println(Arrays.toString(it)));
        } catch (Exception e) {
            LOGGER.error("Error to read CSV: " + csvFile, e);
        } finally {
            if (null != csvReader) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    LOGGER.warn("Error to close CSVReader !", e);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CSVUtil.INSTANCE.readAll("C:\\Users\\user\\Desktop\\histable.csv");
    }

}
