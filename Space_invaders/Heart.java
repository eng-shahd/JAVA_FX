
package space2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Heart  extends Pane{
    Image heartImage = new Image("file:///D:/heart.png");
    ImageView heartView = new ImageView(heartImage);
    
    public Heart (){
             
       heartView.setFitHeight(40);
       heartView.setFitWidth(40);
       getChildren().add(heartView);
    }

    public void setX(double x){
       this.heartView.setLayoutX(x); 
    }
    public void setY(double y){
       this.heartView.setLayoutY(y); 
    }
}
