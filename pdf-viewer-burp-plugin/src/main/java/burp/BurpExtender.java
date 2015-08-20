package burp;


import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender, IMessageEditorTabFactory {

    public IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;


    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {

        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        this.callbacks.setExtensionName("PDF Viewer");

        PrintWriter stdout = new PrintWriter(callbacks.getStdout(), true);
        stdout.println("== PDF Viewer plugin ==");
        stdout.println("Additional tab to preview PDF files directly in Burp.");
        stdout.println(" - Github : https://github.com/h3xstream/burp-pdf-viewer");
        stdout.println("");
        stdout.println("== License ==");
        stdout.println("This plugin use the library ICEpdf licensed under Apache License 2.0");
        stdout.println(" - http://www.icesoft.org/java/projects/ICEpdf/overview.jsf");
        stdout.println("");

        Log.setLogger(new Log.Logger() {
            @Override
            protected void print(String message) {
                try {
                    callbacks.getStdout().write(message.getBytes());
                    callbacks.getStdout().write('\n');
                } catch (IOException e) {
                    System.err.println("Error while printing the log : " + e.getMessage()); //Very unlikely
                }
            }
        });
        Log.DEBUG();

        this.callbacks.registerMessageEditorTabFactory(this);

    }

    @Override
    public IMessageEditorTab createNewInstance(IMessageEditorController iMessageEditorController, boolean b) {
        return new PdfPreviewTab(this.callbacks, this.helpers, iMessageEditorController);
    }
}
