package com.ftn.redditClone.elastic.lucene.handlers;

import com.ftn.redditClone.elastic.model.CommunityElastic;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.io.RandomAccessRead;

import java.io.File;
import java.io.IOException;

public class PDFHandler extends DocumentHandler{
    @Override
    public CommunityElastic getIndexUnit(File file) {
        CommunityElastic retVal = new CommunityElastic();
        try {
            PDFParser parser = new PDFParser((RandomAccessRead) new RandomAccessFile(file, "r"));
            parser.parse();
            String text = getText(parser);
            retVal.setPdfDescription(text);

            PDDocument pdf = parser.getPDDocument();
            PDDocumentInformation info = pdf.getDocumentInformation();

            retVal.setFilename(file.getCanonicalPath());

            pdf.close();
        } catch (IOException e) {
            System.out.println("Error while converting document to PDF");
        }

        return retVal;
    }


    public String getText(PDFParser parser) {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            return textStripper.getText(parser.getPDDocument());
        } catch (IOException e) {
            System.out.println("Error while converting document to PDF");
        }
        return null;
    }
}
