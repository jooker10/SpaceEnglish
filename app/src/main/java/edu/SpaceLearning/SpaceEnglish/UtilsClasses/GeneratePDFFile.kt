package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

import android.util.Log;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GeneratePDFFile {

    // List of column names for the table
    private final ArrayList<String> columnsNamesList = new ArrayList<>();

    /**
     * Generates a PDF file containing a table with data from the provided Category list.
     *
     * @param tableTitle       Title of the table
     * @param pdfFile          Output PDF file
     * @param contentTableList List of Category objects containing data for the table
     */
    public void generate(String tableTitle, File pdfFile, ArrayList<Category> contentTableList) {

        // Define column headers for the table
        columnsNamesList.add("ID");
        columnsNamesList.add("English");
        columnsNamesList.add(Utils.nativeLanguage); // Native language determined by Utils

        try {
            // Initialize PDF writer and document
            FileOutputStream outputStream = new FileOutputStream(pdfFile);
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            PageSize pageSize = PageSize.A4;
            pdfDocument.setDefaultPageSize(pageSize);
            Document document = new Document(pdfDocument);

            // Add title to the PDF document
            Paragraph titleParagraph = new Paragraph(tableTitle)
                    .setFontSize(24)
                    .setBold()
                    .setFontColor(new DeviceRgb(255, 0, 0))
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(titleParagraph);

            // Create table with defined column widths
            float columnWidth = pageSize.getWidth() / columnsNamesList.size();
            float[] columnWidths = {columnWidth, columnWidth, columnWidth};
            Table table = new Table(columnWidths);

            // Add headers to the table
            for (String columnName : columnsNamesList) {
                table.addCell(columnName);
                table.setFontColor(new DeviceRgb(0, 0, 255));
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

    /**
     * Returns the appropriate native language field from the Category object based on Utils.nativeLanguage.
     *
     * @param category Category object containing data
     * @return Native language field value (Spanish, Arabic, or French)
     */
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

}
