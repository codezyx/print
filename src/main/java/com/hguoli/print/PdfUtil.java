package com.hguoli.print;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.FileNotFoundException;

import static com.itextpdf.kernel.pdf.PdfName.BaseFont;

public enum PdfUtil {

    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtil.class);

    public static void main(String[] args) {
        PdfUtil.INSTANCE.createPdf("C:\\Users\\user\\Desktop\\test.pdf");
    }

    public void createPdf(String pdfFile) {
        Document doc = null;
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFile));
            doc = new Document(pdfDocument, PageSize.A4);
            doc.setFont(PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",true));
            doc.add(createTable());
        } catch (Exception e) {
            LOGGER.error("Error to write pdf: " + pdfFile, e);
        } finally {
            if (null != doc) {
                doc.close();
            }
        }
    }

    private Table createTable() {
        Table table = new Table(3);
        table.addCell("1111");
        table.addCell("中文");
        table.addCell("333");
        return table;
    }
}
