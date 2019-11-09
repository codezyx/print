package com.hguoli.print.pdf;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.property.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PageHeader implements IEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageHeader.class);

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = docEvent.getDocument();
        PdfPage page = docEvent.getPage();

        Rectangle pageSize = page.getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(page.getLastContentStream(), page.getResources(), pdf);
        Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
        try {
            PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
            canvas.setFont(font);
        } catch (IOException e) {
            LOGGER.warn("Error to set pdf header font!", e);
        }
        float x = (pageSize.getLeft() + pageSize.getRight()) / 2;
        float y = pageSize.getTop() - 65;
        String pageNumString = "第" + pdf.getPageNumber(page) + "页";
        canvas.showTextAligned(pageNumString, x, y, TextAlignment.CENTER);
    }
}
