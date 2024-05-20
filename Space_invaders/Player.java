
package space2;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import static space2.Spacce.SCENE_HEIGHT;
import static space2.Spacce.SCENE_WIDTH;


public class Player extends Pane {
    public static final double PLAYER_HEIGHT= 100;
    public static final double PLAYER_WIDTH= 100;
    
    // set player multiple shape
    private Image playerImage0 = new Image("file:///D:/player.png");
    private Image playerImage1 = new Image("file:///D:/4.png");
    private Image playerImage2 = new Image("file:///D:/3.png");
    private ImageView playerImageView =new ImageView(playerImage0);
     
    public  boolean  checkBorder_right=false; // must be access in main
    public  boolean  checkBorder_left=false;

    public Player(){
       setPrefSize(SCENE_WIDTH,SCENE_HEIGHT);
       playerImageView.setFitHeight(PLAYER_HEIGHT);
       playerImageView.setFitWidth(PLAYER_WIDTH);
       playerImageView.setX(Spacce.SCENE_WIDTH/2-0.5*PLAYER_WIDTH);
       playerImageView.setY(Spacce.SCENE_HEIGHT-PLAYER_HEIGHT);
       setPrefSize(SCENE_HEIGHT,SCENE_WIDTH);
       getChildren().add(playerImageView);
    }

    public void setX(double x) {
        playerImageView.setX(playerImageView.getX()+x);
    }
    public void setY(double y) {
        playerImageView.setX(playerImageView.getY()+y);
    }
    
    // get x,y where bullet start
    public  double getX() {
       return playerImageView.getX( )+0.5*PLAYER_WIDTH;
    }
    public double getY() {
       return playerImageView.getY( );
    }

    public void setCheckBorder_right(boolean checkBorder_right) {
        this.checkBorder_right = checkBorder_right;
    }

    public void setCheckBorder_left(boolean checkBorder_left) {
        this.checkBorder_left = checkBorder_left;
    }
    
    public void checkBorder(){
        if((playerImageView.getX()+PLAYER_WIDTH>=SCENE_WIDTH)){  
            setCheckBorder_right(true);          //block right and allow left (movement)
            setCheckBorder_left(false);
        }
        else if(playerImageView.getX()<=0){
            setCheckBorder_left(true);           //block left and allow right  (movement)
            setCheckBorder_right(false);
            
        }
        else {
           setCheckBorder_left(false);           //allow left and  right  (movement)
           setCheckBorder_right(false);
        }
        
    }
       public Bounds getPlayerBounds() {
        return playerImageView .getBoundsInParent();
    }
       public void setPlayerImag1(){
           playerImageView.setImage(playerImage1);
       }
       public void setPlayerImag0(){
           playerImageView.setImage(playerImage0);
       }
       public void setPlayerImag2(){
           playerImageView.setImage(playerImage2);
       }

  
}
