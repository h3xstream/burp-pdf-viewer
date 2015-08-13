package com.h3xstream.burp.pdfviewer;

import com.esotericsoftware.minlog.Log;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.views.DocumentViewControllerImpl;

import javax.swing.*;
import java.awt.*;

public class PdfPreviewPanel {

    private SwingController controller;
    private JPanel viewerComponentPanel;
    private boolean openDocument = false;

    public PdfPreviewPanel() {
        buildPanel();
    }

    public void buildPanel() {
        Log.debug("Instantiating a new panel..");

        controller = new SwingController();

        SwingViewBuilder factory = new SwingViewBuilder(controller);

        viewerComponentPanel = factory.buildViewerPanel();

        //Add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new MyAnnotationCallback(controller.getDocumentViewController()));

        //controller.setZoom();
    }

    public Component getComponent() {
        return viewerComponentPanel;
    }

    public void setPdfContent(byte[] respBytes, int bodyOffset,String description,String url) {
        if(openDocument) {
            controller.closeDocument();
            openDocument=false;
        }

        controller.openDocument(respBytes, bodyOffset, respBytes.length - bodyOffset,description,url);
        openDocument = true;

        controller.setPageFitMode(DocumentViewControllerImpl.PAGE_FIT_WINDOW_WIDTH,true);
    }
}
