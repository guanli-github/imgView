package viewer.Extractor;

import javafx.scene.image.Image;

import java.io.File;

public interface IExtractor {
    static void reInit(File file){};
    static Image getImage(int page){return null;};
}
