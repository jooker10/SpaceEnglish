package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

import android.util.Log;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GeneratePdf {

    public void generateTheFile(String tableTitle, File pdfFile, ArrayList<Category> contentTableList) {
        ArrayList<String> columnsNamesList = new ArrayList<>();
        columnsNamesList.add("ID");
        columnsNamesList.add("English");
        columnsNamesList.add(Utils.nativeLanguage); // Native language determined by Utils

        try (FileOutputStream outputStream = new FileOutputStream(pdfFile)) {
            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setDefaultPageSize(PageSize.A4);

            // Add header and footer event handlers
            pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, new HeaderEventHandler(tableTitle));
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterEventHandler());

            Document document = new Document(pdfDocument);

            // Create table with defined column widths
            float[] columnWidths = {1, 1, 1};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));

            // Add headers to the table
            for (String columnName : columnsNamesList) {
                table.addCell(columnName);
            }

            // Add data rows to the table
            for (Category row : contentTableList) {
                table.addCell(String.valueOf(row.getCategoryID())); // ID column
                table.addCell(row.getCategoryEng()); // English column
                table.addCell(nativeLanguage(row)); // Native language column
            }

            // Add the table to the document and close it
            document.add(table);
            document.close();

        } catch (IOException e) {
            Log.e("GeneratePDFFile", "Error generating PDF file", e);
        }
    }

    private String nativeLanguage(Category category) {
        switch (Utils.nativeLanguage) {
            case Constants.LANGUAGE_NATIVE_SPANISH:
                return category.getCategorySp(); // Spanish language field
            case Constants.LANGUAGE_NATIVE_ARABIC:
                return category.getCategoryAr(); // Arabic language field
            default:
                return category.getCategoryFr(); // Default to French language field
        }
    }

    private class HeaderEventHandler implements IEventHandler {
        private final String header;

        public HeaderEventHandler(String header) {
            this.header = header;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            PdfDocument pdfDoc = docEvent.getDocument();
            int pageNumber = pdfDoc.getPageNumber(page);
            Document doc = new Document(pdfDoc);

            // Add header
            /*Paragraph headerParagraph = new Paragraph(header)
                    .setFontSize(12)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);*/
            doc.showTextAligned(header, 20f, 20f, TextAlignment.CENTER, 0);

        }
    }

    private class FooterEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            PdfDocument pdfDoc = docEvent.getDocument();
            int pageNumber = pdfDoc.getPageNumber(page);
            Document doc = new Document(pdfDoc);

            // Add footer
          //  Paragraph footerParagraph = new Paragraph(String.format("Page %d", pageNumber))
                  /*  .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER);*/
            doc.showTextAligned(String.format("Page %d", pageNumber), 297.5f, 20, TextAlignment.CENTER, 0);
        }
    }
}