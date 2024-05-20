
package space2;

import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


 public class Bullet extends Pane {
    private final double BULLET_HEIGHT= 40;
    private final double BULLET_WIDTH= 18;
    private final double BULLET_SPEED= 0.6;
    private double bulletPosition=0;
    private Image bulletImage =new Image("file:///D:/bullet.png");
    private ImageView bulletImageView = new ImageView (bulletImage);
    
    public Bullet ( double x,double y){
        bulletImageView.setFitHeight(BULLET_HEIGHT);
        bulletImageView.setFitWidth(BULLET_WIDTH);
        bulletImageView.setX(x-0.5*BULLET_WIDTH);
        bulletImageView.setY(y-BULLET_HEIGHT);     
        getChildren().add(bulletImageView);
        AnimationTimer bulletAnimation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update the position of the background
                bulletPosition -= BULLET_SPEED;
                
                setLayoutY(getLayoutY()+bulletPosition);
                
            }
        };
        bulletAnimation.start();
    }
     public Bounds getBounds() {
        return bulletImageView.getBoundsInParent();
    }

    void stopAnimation() {
        this.setVisible(false); 
        //this.setManaged(false); // Stop managing the bullet in the layout
    }
    
    public void updatePosition() {
         // Move the bullet upward by decreasing its Y position
        double newY = bulletImageView.getY() - BULLET_SPEED;
        bulletImageView.setY(newY);

        // Check if the bullet has moved off the top of the screen
        if (newY + bulletImageView.getFitHeight() < 0) {
            this.setVisible(false); // Make the bullet invisible
            this.setManaged(false); // Stop managing the bullet in the layout
            
        }
       
    }

   
    
}
