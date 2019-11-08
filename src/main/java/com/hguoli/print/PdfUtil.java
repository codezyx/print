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
import java.util.ArrayList;
import java.util.Collections;
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
        PdfUtil.INSTANCE.createDoc(list, "C:\\Users\\user\\Desktop\\test.pdf");
    }

    public void createDoc(List<String[]> data, String pdfFile) {
        Document doc = null;
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFile));
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new PageFooter());
            pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, new PageHeader());
            doc = new Document(pdfDocument, PageSize.A4);
            doc.setTopMargin(72);
            doc.setFont(SONG_TI_FONT);
            doc.setFontSize(8);

            addTable(doc, data);

        } catch (Exception e) {
            LOGGER.error("Error to write pdf ", e);
        } finally {
            if (null != doc) {
                doc.close();
            }
        }
    }

    public void addTable(Document doc, List<String[]> data) {
        if (null == data || data.isEmpty() || null == doc) {
            return;
        }
        // table header 分组
        String[] headers = data.get(FIRST_INDEX);
        int headerSize = headers.length;
        List<List<Integer>> headerList = new ArrayList<>();
        int tableColumnSize = 8;
        List<Integer> oneHeader = new ArrayList<>(tableColumnSize);
        for (int i = 1; i < headerSize; i++) {
            if (i % 7 != 0) {
                oneHeader.add(i);
            } else {
                oneHeader.add(i);
                oneHeader.add(FIRST_INDEX);
                Collections.sort(oneHeader);
                headerList.add(oneHeader);
                oneHeader = new ArrayList<>(tableColumnSize);
            }
        }
        if (!oneHeader.isEmpty()) {
            oneHeader.add(FIRST_INDEX);
            Collections.sort(oneHeader);
            headerList.add(oneHeader);
        }
        if (!headerList.isEmpty()) {
            for (List<Integer> index : headerList) {
                doc.add(createOnePageTable(data, index));
            }
        }
    }

    private Table createOnePageTable(List<String[]> data, List<Integer> indexes) {
        Table table = new Table(indexes.size());
        table.setHorizontalAlignment(HorizontalAlignment.LEFT);
        table.setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            if (isNotEmpty(row)) {
                for (int j = 0; j < indexes.size(); j++) {
                    Cell cell = new Cell();
                    cell.setWidth((j == 0) ? 100 : 50);
                    cell.add(new Paragraph(row[indexes.get(j)]));
                    table.addCell(cell);
                }
            }
        }
        return table;
    }

    public void createPdf(List<String[]> data, List<Integer> indexes, String pdfFile) {
        Document doc = null;
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFile));
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new PageFooter());
            pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, new PageHeader());
            doc = new Document(pdfDocument, PageSize.A4);
            doc.setTopMargin(72);
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
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 添加表头
        String[] headers = data.get(FIRST_INDEX);
        for (int i = 0; i < indexes.size(); i++) {
            Integer index = indexes.get(i);
            Cell cell = new Cell();
            cell.setWidth((i == 0) ? 100 : 50);
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
