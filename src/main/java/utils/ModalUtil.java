package utils;

import javafx.scene.Node;

/**
 * 隐藏层的显示和隐藏
 */
public class ModalUtil {
    public static void show(Node modal, Node main){
        main.setOpacity(0.1);
        main.toBack();
        //main.setManaged(false);
        modal.setManaged(true);
        modal.setVisible(true);
    }
    public static void hide(Node modal, Node main){
        modal.setVisible(false);
        modal.setManaged(false);
        //main.setManaged(true);
        main.setOpacity(1.0);
        main.toFront();
    }
}
