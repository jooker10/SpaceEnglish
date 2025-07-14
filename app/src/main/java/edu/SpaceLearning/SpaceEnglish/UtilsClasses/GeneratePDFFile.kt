package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.util.Log
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class GeneratePDFFile {
    // List of column names for the table
    private val columnsNamesList = ArrayList<String>()

    /**
     * Generates a PDF file containing a table with data from the provided Category list.
     *
     * @param tableTitle       Title of the table
     * @param pdfFile          Output PDF file
     * @param contentTableList List of Category objects containing data for the table
     */
    /*fun generate(tableTitle: String?, pdfFile: File?, contentTableList: ArrayList<Category>) {
        // Define column headers for the table

        columnsNamesList.add("ID")
        columnsNamesList.add("English")
        columnsNamesList.add(Utils.nativeLanguage) // Native language determined by Utils

        try {
            // Initialize PDF writer and document
            val outputStream = FileOutputStream(pdfFile)
            val writer = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(writer)
            val pageSize = PageSize.A4
            pdfDocument.defaultPageSize = pageSize
            val document = Document(pdfDocument)

            // Add title to the PDF document
            val titleParagraph = Paragraph(tableTitle)
                .setFontSize(24f)
               // .setBold()
                .setFontColor(DeviceRgb(255, 0, 0))
                .setTextAlignment(TextAlignment.CENTER)
            document.add(titleParagraph)

            // Create table with defined column widths
            val columnWidth = pageSize.width / columnsNamesList.size
            val columnWidths = floatArrayOf(columnWidth, columnWidth, columnWidth)
            val table = Table(columnWidths)

            // Add headers to the table
            for (columnName in columnsNamesList) {
                table.addCell(columnName)
                table.setFontColor(DeviceRgb(0, 0, 255))
            }

            // Add data rows to the table
            for (row in contentTableList) {
                table.addCell(row.categoryID.toString()) // ID column
                table.addCell(row.categoryEng) // English column
                table.addCell(nativeLanguage(row)) // Native language column
            }

            // Add the table to the document and close it
            document.add(table)
            document.close()
        } catch (e: IOException) {
            Log.e("GeneratePDFFile", "Error generating PDF file", e)
        }
    }*/
    fun generate(
        tableTitle: String?,
        pdfFile: File?,
        contentTableList: ArrayList<Category>,
        onComplete: (success: Boolean, file: File?) -> Unit
    ) {
        columnsNamesList.clear()
        columnsNamesList.add("ID")
        columnsNamesList.add("English")
        columnsNamesList.add(Utils.nativeLanguage)

        try {
            val outputStream = FileOutputStream(pdfFile)
            val writer = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(writer)
            val pageSize = PageSize.A4
            pdfDocument.defaultPageSize = pageSize
            val document = Document(pdfDocument)

            val titleParagraph = Paragraph(tableTitle)
                .setFontSize(24f)
                .setFontColor(DeviceRgb(255, 0, 0))
                .setTextAlignment(TextAlignment.CENTER)
            document.add(titleParagraph)

            val columnWidth = pageSize.width / columnsNamesList.size
            val columnWidths = floatArrayOf(columnWidth, columnWidth, columnWidth)
            val table = Table(columnWidths)

            for (columnName in columnsNamesList) {
                table.addCell(columnName)
            }

            for (row in contentTableList) {
                table.addCell(row.categoryID.toString())
                table.addCell(row.categoryEng)
                table.addCell(nativeLanguage(row))
            }

            document.add(table)
            document.close()

            // ✅ Notify caller PDF is ready
            onComplete(true, pdfFile)

        } catch (e: IOException) {
            Log.e("GeneratePDFFile", "Error generating PDF file", e)
            onComplete(false, pdfFile)
        }
    }


    /**
     * Returns the appropriate native language field from the Category object based on Utils.nativeLanguage.
     *
     * @param category Category object containing data
     * @return Native language field value (Spanish, Arabic, or French)
     */
    private fun nativeLanguage(category: Category): String {
        return when (Utils.nativeLanguage) {
            Constants.LANGUAGE_NATIVE_SPANISH -> category.categorySp // Spanish language field
            Constants.LANGUAGE_NATIVE_ARABIC -> category.categoryAr // Arabic language field
            else -> category.categoryFr // Default to French language field
        }
    }
}
