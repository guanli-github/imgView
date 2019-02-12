package extractor;

import data.Const;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pdf {
    private static Document document = new Document();
    private static float scale=1.0f; //缩放比例
    private static float rotation=0f; //旋转角度

    static void reInit(File file){
        FileParser.fileType = Const.TYPE_PDF;
        try {
            document.setFile(file.getAbsolutePath());
        } catch (IOException | PDFException | PDFSecurityException e) {
            e.printStackTrace();
        }
        FileParser.totalPage = document.getNumberOfPages();
    }
    public static Image getImage(int page){
        BufferedImage image=null;
        try {
            image=(BufferedImage)document.getPageImage(page,
                    GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX,
                    rotation,scale);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return SwingFXUtils.toFXImage(image,null);
        }
    }
}
