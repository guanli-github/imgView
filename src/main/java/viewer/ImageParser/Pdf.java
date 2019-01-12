package viewer.ImageParser;

import data.Const;
import data.Status;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pdf {
    private static PDFRenderer renderer;

    static void reInitPdf(File file){
        FileParser.fileType = Const.TYPE_PDF;
        PDDocument doc = null;
        try {
            doc = PDDocument.load(file);
            renderer = new PDFRenderer(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileParser.totalPage = doc.getNumberOfPages();
    }

    static Image getImageFromPdf(int page){
        BufferedImage image = null;
        try {
            image = renderer.renderImageWithDPI(page, Status.renderDpi);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return SwingFXUtils.toFXImage(image,null);
        }
    }
}
