package burp;

import com.esotericsoftware.minlog.Log;
import com.h3xstream.burp.pdfviewer.PdfPreviewPanel;
import com.h3xstream.burp.pdfviewer.PdfUtil;

import java.awt.*;

public class PdfPreviewTab implements IMessageEditorTab {

    private byte[] message;
    private PdfPreviewPanel propertyPanel;

    private IExtensionHelpers helpers;
    private IBurpExtenderCallbacks callbacks;
    private IMessageEditorController controller;

    PdfPreviewTab(IBurpExtenderCallbacks callbacks, IExtensionHelpers helpers, IMessageEditorController controller) {
        this.helpers = helpers;
        this.callbacks = callbacks;
        this.controller = controller;

        propertyPanel = new PdfPreviewPanel();

        callbacks.customizeUiComponent(propertyPanel.getComponent());
    }




    @Override
    public String getTabCaption() {
        return "PDF";
    }

    @Override
    public Component getUiComponent() {
        return propertyPanel.getComponent();
    }

    @Override
    public boolean isEnabled(byte[] respBytes, boolean isRequest) {
        if (isRequest) {
            return false;
        } else { //The tab will appears if the response is a PDF
            IResponseInfo responseInfo = helpers.analyzeResponse(respBytes);
            int bodyOffset = responseInfo.getBodyOffset();
            return PdfUtil.isPdfFile(respBytes, bodyOffset);
        }
    }

    @Override
    public void setMessage(byte[] respBytes, boolean isRequest) {
        this.message = respBytes;
        //Clear the component??

        try {
            IResponseInfo responseInfo = helpers.analyzeResponse(respBytes);
            IRequestInfo  requestInfo  = helpers.analyzeRequest(controller.getRequest());
            int bodyOffset = responseInfo.getBodyOffset();

            String path = getPathRequested(requestInfo);

            propertyPanel.setPdfContent(respBytes, bodyOffset, path, path);

        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    @Override
    public byte[] getMessage() {
        return message;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public byte[] getSelectedData() {
        return this.message;
    }


    public static String getPathRequested(IRequestInfo request) {
        String h = request.getHeaders().get(0);
        return h.substring(h.indexOf(" ") + 1, h.lastIndexOf(" "));
    }


}
