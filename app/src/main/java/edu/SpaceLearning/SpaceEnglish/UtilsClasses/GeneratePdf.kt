package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.util.Log

import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class GeneratePdf {}

/*
class GeneratePdf {
    fun generateTheFile(
        tableTitle: String?,
        pdfFile: File?,
        contentTableList: ArrayList<Category>
    ) {
        val columnsNamesList = ArrayList<String>()
        columnsNamesList.add("ID")
        columnsNamesList.add("English")
        columnsNamesList.add(Utils.nativeLanguage) // Native language determined by Utils

        try {
            FileOutputStream(pdfFile).use { outputStream ->
                // Initialize PDF writer and document
                val writer = PdfWriter(outputStream)
                val pdfDocument = PdfDocument(writer)
                pdfDocument.defaultPageSize = PageSize.A4

                // Add header and footer event handlers
                pdfDocument.addEventHandler(
                    PdfDocumentEvent.START_PAGE,
                    HeaderEventHandler(tableTitle)
                )
                pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, FooterEventHandler())

                val document = Document(pdfDocument)

                // Create table with defined column widths
                val columnWidths = floatArrayOf(1f, 1f, 1f)
                val table = Table(UnitValue.createPercentArray(columnWidths))
                table.setWidth(UnitValue.createPercentValue(100f))

                // Add headers to the table
                for (columnName in columnsNamesList) {
                    table.addCell(columnName)
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
            }
        } catch (e: IOException) {
            Log.e("GeneratePdfFile", "Error generating PDF file", e)
        }
    }

    private fun nativeLanguage(category: Category): String {
        return when (Utils.nativeLanguage) {
            Constants.LANGUAGE_NATIVE_SPANISH -> category.categorySp // Spanish language field
            Constants.LANGUAGE_NATIVE_ARABIC -> category.categoryAr // Arabic language field
            else -> category.categoryFr // Default to French language field
        }
    }

    private inner class HeaderEventHandler(private val header: String?) : IEventHandler {
        override fun handleEvent(event: Event) {
            val docEvent = event as PdfDocumentEvent
            val page = docEvent.page
            val pdfDoc = docEvent.document
            val pageNumber = pdfDoc.getPageNumber(page)
            val doc = Document(pdfDoc)

            // Add header
            */
/*Paragraph headerParagraph = new Paragraph(header)
                    .setFontSize(12)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);*//*

            doc.showTextAligned(header, 20f, 20f, TextAlignment.CENTER, 0f)
        }
    }

    private inner class FooterEventHandler : IEventHandler {
        override fun handleEvent(event: Event) {
            val docEvent = event as PdfDocumentEvent
            val page = docEvent.page
            val pdfDoc = docEvent.document
            val pageNumber = pdfDoc.getPageNumber(page)
            val doc = Document(pdfDoc)

            // Add footer
            //  Paragraph footerParagraph = new Paragraph(String.format("Page %d", pageNumber))
            */
/*  .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER);*//*

            doc.showTextAligned(
                String.format("Page %d", pageNumber),
                297.5f,
                20f,
                TextAlignment.CENTER,
                0f
            )
        }
    }
}*/
