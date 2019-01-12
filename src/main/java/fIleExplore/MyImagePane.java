package fIleExplore;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/*
 * 用于显示图片的工具类
 * 封装成一个容器
 * 这个容器显示整张图片
 * 适应父窗口
 */

public class MyImagePane extends Pane {
    ImageView imageView = new ImageView();
    Image image;

    public MyImagePane() {
        // 添加图片
        getChildren().add(imageView);
    }

    public void showImage(Image image) {
        this.image = image;
        imageView.setImage(image);
        layout();
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();

        // 对ImageView进行摆放，使其适应父窗口
        imageView.resizeRelocate(0, 0, w, h);
        imageView.setFitWidth(w);
        imageView.setFitHeight(h);
        imageView.setPreserveRatio(true);
    }
}