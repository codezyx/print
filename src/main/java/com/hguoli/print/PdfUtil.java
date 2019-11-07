package com.hguoli.print;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.List;

public enum PdfUtil {

    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtil.class);
    private static final int FIRST_INDEX = 0;
    public static PdfFont SONG_TI_FONT = null;

    static {
        try {
            SONG_TI_FONT = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        } catch (IOException e) {
            LOGGER.error("Error to init STSong-Light font!", e);
        }
    }

    public static void main(String[] args) {
        List<String[]> list = CSVUtil.INSTANCE.readAll("C:\\Users\\user\\Desktop\\histable.csv");
        PdfUtil.INSTANCE.createPdf(list, Arrays.asList(0, 1, 2, 3, 4), "C:\\Users\\user\\Desktop\\test.pdf");
    }

    public void print() {
    }

    public void createPdf(List<String[]> data, List<Integer> indexes, String pdfFile) {
        Document doc = null;
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFile));
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new PageFooter());
//            pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, new PageHeader());
            doc = new Document(pdfDocument, PageSize.A4);
            doc.setFont(SONG_TI_FONT);
            doc.setFontSize(8);
            Table table = createTable(data, indexes);
            if (null != table) {
                doc.add(table);
            }
        } catch (Exception e) {
            LOGGER.error("Error to write pdf: " + pdfFile, e);
        } finally {
            if (null != doc) {
                doc.close();
            }
        }
    }

    private Table createTable(List<String[]> data, List<Integer> indexes) {
        if (data.isEmpty() || null == indexes || indexes.isEmpty()) {
            return null;
        }
        Table table = new Table(indexes.size());
        table.setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 添加表头
        String[] headers = data.get(FIRST_INDEX);
        for (Integer index : indexes) {
            Cell cell = new Cell();
//            cell.setPaddingLeft(20);
            cell.add(new Paragraph(headers[index]));
            table.addHeaderCell(cell);
        }
        // 添加数据
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            if (isNotEmpty(row)) {
                for (Integer index : indexes) {
                    table.addCell(row[index]);
                }
            }
        }
        return table;
    }

    /**
     * 判断一行数据是否都是空
     *
     * @param row String[]
     * @return boolean
     */
    private boolean isNotEmpty(String[] row) {
        if (null == row || row.length == 0) {
            return false;
        } else {
            for (String dat : row) {
                if (null != dat && !"".equals(dat.trim())) {
                    return true;
                }
            }
            return false;
        }
    }
}
