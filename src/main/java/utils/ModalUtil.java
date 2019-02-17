package utils;

import javafx.scene.Node;

/**
 * 隐藏层的显示和隐藏
 */
public class ModalUtil {
    public static void show(Node main,Node... modals){
        main.setOpacity(0.2);
        main.toBack();
        for(Node modal:modals){
            modal.setManaged(true);
            modal.setVisible(true);
        }

    }
    public static void hide(Node main,Node... modals){
        for(Node modal:modals){
            modal.setVisible(false);
            modal.setManaged(false);
        }

        main.setOpacity(1.0);
        main.toFront();
    }
}
