package com.h3xstream.burp.pdfviewer;

public class PdfUtil {

    public static boolean isPdfFile(byte[] respBytes,int bodyOffset) {
        return respBytes[bodyOffset] == (byte) '%' && //
                respBytes[bodyOffset+1] == (byte) 'P' && //
                respBytes[bodyOffset+2] == (byte) 'D' && //
                respBytes[bodyOffset+3] == (byte) 'F';
    }
}
