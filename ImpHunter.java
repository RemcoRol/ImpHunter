package com.remco1337.bots.ImpHunter;

import com.remco1337.bots.ImpHunter.ui.ImpCatcherUIController;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.util.Resources;
import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class ImpHunter extends TreeBot implements EmbeddableUI{

    private ObjectProperty<Node> botInterfaceProperty;

    @Override
    public TreeTask createRootTask() {
        return new IsInImpArea();
    }



    @Override
    public void onStart(String... args) {
        Settings setting = new Settings();
        setting.firstLoad();
        if (setting.gettingDebugBot()) {
            System.out.println("ImpHunter.java: started");
        }
    }

    public ImpHunter() {
        setEmbeddableUI(this);
    }

    @Override
    public ObjectProperty<Node> botInterfaceProperty() {
        if (botInterfaceProperty == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(new ImpCatcherUIController());
            try {
                Node node = loader.load(Resources.getAsStream("com/remco1337/bots/ImpHunter/ui/impcatcher_ui.fxml"));
                botInterfaceProperty = new SimpleObjectProperty<>(node);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return botInterfaceProperty;
    }


    public static void main(String[] args) {
    }
}
