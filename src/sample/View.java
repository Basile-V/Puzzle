package sample;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.models.Agent;
import sample.models.GridObservable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private TilePane pane;
    private int nbRows;
    private int nbColumns;
    private int tileWidth = 100;
    private int tileHeight = 100;

    public View(TilePane pane, GridObservable env, int sizeX, int sizeY) {
        nbRows = sizeY;
        nbColumns = sizeX;
        this.pane = pane;
        pane.setPrefTileHeight(tileHeight);
        pane.setPrefTileWidth(tileWidth);
        pane.setPrefColumns(sizeX);
        pane.setPrefRows(sizeY);
        pane.setPrefSize(tileWidth*sizeX,tileHeight*sizeY);

        for(int y=0; y<sizeY; y++){
            for(int x=0; x<sizeX; x++){
                Agent a = env.getGridAtPos(x, y);
                pane.getChildren().add(generateAgentNode(a));
            }
        }


    }

    private Node generateAgentNode(Agent a){
        if (a == null){
            Rectangle r = new Rectangle();
            r.setFill(Color.GOLD);
            r.setWidth(tileWidth);
            r.setHeight(tileHeight);
            return r;
        }else{
            Label t = new Label(String.valueOf(a.getNom()));
            t.setPrefSize(tileWidth, tileHeight);
            t.setStyle("-fx-text-fill: black; -fx-background-color:salmon; -fx-font-size: 50; -fx-alignment: center;");
            return t;
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (evt.getPropertyName()) {
                    case "agentMoved" -> agentMoved(evt);
                }
            }
        });
    }

    private void agentMoved(PropertyChangeEvent evt){
        int[] oldPos = (int[]) evt.getOldValue();
        int[] newPos = (int[]) evt.getNewValue();
        int oI = indexFromPos(oldPos);
        int nI = indexFromPos(newPos);
        Node a = pane.getChildren().set(oI, generateAgentNode(null));
        Node c = pane.getChildren().set(nI, a);
    }

    private int indexFromPos(int[] pos){
        return pos[1]*nbColumns + pos[0];
    }
}
