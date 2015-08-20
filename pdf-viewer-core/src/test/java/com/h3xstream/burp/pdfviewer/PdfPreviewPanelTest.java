package com.h3xstream.burp.pdfviewer;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;

/**
 * This is a small integration test to validate change done to the UI.
 * The filePath need to be change.
 */
public class PdfPreviewPanelTest {

    public static void main(String[] args) throws IOException {
        // Get a file from the command line to open
        String filePath = "C:/Users/Philippe/Desktop/whitepaper v4.pdf";

        byte[] pdfFile = getBytesFromFile(new File(filePath));
        System.out.println(Arrays.toString(pdfFile));
        PdfPreviewPanel pdfPanel = new PdfPreviewPanel();
        pdfPanel.setPdfContent(pdfFile, 0, "", filePath);

        JFrame applicationFrame = new JFrame();
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.getContentPane().add(pdfPanel.getComponent());

        // show the component
        applicationFrame.pack();
        applicationFrame.setVisible(true);
    }

    private static byte[] getBytesFromFile(File file) throws IOException {

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)file.length()];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                    && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }
}
