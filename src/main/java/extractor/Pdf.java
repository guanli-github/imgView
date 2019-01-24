package extractor;

import data.Const;
import data.Setting;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pdf implements IExtractor{
    private static PDFRenderer renderer;

    static void reInit(File file){
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
    public static Image getImage(int page){
        BufferedImage image = null;
        try {
            image = renderer.renderImageWithDPI(page, Setting.renderDpi);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return SwingFXUtils.toFXImage(image,null);
        }
    }
}
