/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package space2;


import java.util.Iterator;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import static space2.Spacce.SCENE_HEIGHT;
import static space2.Spacce.SCENE_WIDTH;

public class Monsters extends Pane{
    
    private Image monsterImage = new Image("file:///D:/ss.png");
    private static final int MONSTER_WIDTH = 60;
    private static final int MONSTER_HEIGHT = 60;
    private  int MONSTER_SPEED=5;
    private int score=0;
    private Node monster;
    private Bullet bullets;
    private ImageView monsterView ;
    
    Monsters(int n){
        // set image of every monster
        for (int i = 0; i < n; i++) {
            monsterView = new ImageView(monsterImage);
            monsterView.setFitWidth(MONSTER_WIDTH);
            monsterView.setFitHeight(MONSTER_HEIGHT);
            monsterView.setLayoutX(Math.random() * (SCENE_WIDTH- MONSTER_WIDTH));
            monsterView.setLayoutY(-MONSTER_HEIGHT * i);
            getChildren().add(monsterView);
        }
          
        // move monsters down then remove
         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40), e -> {
            for (javafx.scene.Node monster : getChildren()) {
                monster.setLayoutY(monster.getLayoutY() + MONSTER_SPEED ); 
                if (monster.getLayoutY() > SCENE_HEIGHT) {
                   getChildren().remove(monster);
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }

    public void checkCollisions(List<Bullet> bullets) {
        Iterator monsterIterator = getChildren().iterator();
        while (monsterIterator.hasNext()) {
           monster = (Node) monsterIterator.next();
           Iterator<Bullet> bulletIterator = bullets.iterator();
           while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                if (monster.getBoundsInParent().intersects(bullet.getBounds())) {
                    
                    monsterIterator.remove(); // Remove the monster if it collides with a bullet
                    bullet.stopAnimation(); // Stop the bullet's animation
                    bulletIterator.remove(); // Remove the bullet from the list
                    getChildren().remove(bullet);
                   score++;
                  if(score % 4 == 0){  // set speed of monster to make game more hard
                         MONSTER_SPEED += 1;
                    }

                    break; // Exit the loop after handling collision
                }
            }
        }
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
