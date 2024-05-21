/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package space2;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.SPACE;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import space2.Bullet;
import space2.Monsters;
import space2.Player;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.SPACE;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import space2.Bullet;
import space2.Monsters;
import space2.Player;
import space2.Heart;




public class Spacce extends Application {
    
    // Adding sounds
    private static final String BACKGROUND_AUDIO_FILE = "sounds/soundBackGround.mp3";
    private static final String BUTTON_SOUND_FILE = "sounds/button.mp3";
    private static final String START ="sounds/start.mp3";
    private static final String LOSE ="sounds/lose.mp3";
    private static final String LASER ="sounds/laser.mp3";
    private static final String START1="sounds/start1.mp3";
    private static final String REMOVR="sounds/remove.mp3";
    private Sound backgroundPlayer;
    private Sound buttonPlayer;
    private Sound start;
    private Sound startSound;
    private Sound lose;
    private Sound laser;
    private Sound remove;
    
    // adding fotos
    private static final String StartBACKGROUND_IMAGE_URL = "file:///D:/background_str.jpeg";
    private static final String MONSTER_IMAGE_URL = "file:///D:/ss.png";
    private static final String ROCKET_IMAGE_URL = "file:///D:/player.png";
    private static final String Background_IMAGE_URL1 = "file:///D:/blue.jpg";
    private static final String Background_IMAGE_URL2 = "file:///D:/space.jpg";
    private static final String Background_IMAGE_URL3 = "file:///D:/galaxy.jpeg";
    private static final String Background_IMAGE_URL4 ="file:///D:/stars.jpg";
    private Image backgroundImage1 = new Image(Background_IMAGE_URL1);
    private Image backgroundImage2 = new Image(Background_IMAGE_URL2);
    private Image backgroundImage3 = new Image(Background_IMAGE_URL3);
    private Image backgroundImage4 = new Image(Background_IMAGE_URL4);
    private ImageView backgroundImageView = new ImageView(backgroundImage1);
    // declare game variables & constants
    public static final double SCENE_WIDTH = 1000;
    public static final double SCENE_HEIGHT = 600;
    private static final double BACKGROUND_HEIGHT = 1500; 
    private static final double BACKGROUND_SPEED = 1; 
    private double backgroundPosition = -BACKGROUND_HEIGHT;
    private Pane gameRoot = new Pane();
    private Player player = new Player();
    private Stage primaryStage;
    private Scene gameScene = new Scene(gameRoot, SCENE_WIDTH, SCENE_HEIGHT);
    private Scene signInScene;
    private Scene scoreScene;
    private String playerName;
    private  int num_hearts = 3;
    private  int score = 0;  
    private List <Bullet> bullets = new ArrayList<>();
    private Text gameOver=new Text("Game Over");
    private int lastHeart ;
    private Group startWindowGroup = new Group();
    private Scene startWindowScene = new Scene(startWindowGroup,SCENE_WIDTH,SCENE_HEIGHT); 
    private Group optionGroup = new Group();
    private Scene optionScene = new Scene(optionGroup,SCENE_WIDTH,SCENE_HEIGHT); 
    private ArrayList <Heart> heartList=new ArrayList();
    private static final int MONSTER_WIDTH = 60;
    private static final int MONSTER_HEIGHT = 60;
    private Line startGroundLine=new Line(500,350,500,550);
    private PathTransition leftTransitionMonsters;
    private PathTransition rightTransitionMonsters;
    private Timeline heartTime;
    
    @Override
    public void start(Stage primaryStage) {
       this.primaryStage = primaryStage;
       Image icon = new Image("file:///D:/ico.png");
       primaryStage.getIcons().add(icon);
       backgroundPlayer = new Sound(BACKGROUND_AUDIO_FILE);
       backgroundPlayer.getMediaPlayer().setCycleCount(-1);
       buttonPlayer = new Sound(BUTTON_SOUND_FILE);
       start = new Sound (START);
       lose = new Sound (LOSE);
       startSound=new Sound (START1);
       startSound.getMediaPlayer().setCycleCount(-1);
       laser=new Sound (LASER);
       remove=new Sound (REMOVR);
       startSceneShow();
    }
    
    // effect sparkle of every button in startWindow
    private void addMouseHandlers(Text... texts) {
        for (Text text : texts) {
            text.setOnMouseEntered(e -> {
                text.setStroke(Color.color(0.3, 0.8, 1, 1));
                text.setStrokeWidth(3);
            });
            text.setOnMouseExited(e -> text.setStroke(Color.TRANSPARENT));
        }
    }
     
     // rocket adder between button in startWindow
    private HBox createTextWithRocket(Image rocket, Text text) {
        ImageView leftRocket = new ImageView(rocket);
        leftRocket.setFitHeight(50);
        leftRocket.setFitWidth(50);

        ImageView rightRocket = new ImageView(rocket);
        rightRocket.setFitHeight(50);
        rightRocket.setFitWidth(50);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(leftRocket, text, rightRocket);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }
      
      
    // setting of every button in startWindow
    private Text createText(String text, double fontSize, int glow) {
        Text t = new Text(text);
        t.setFont(Font.font("Andalus", fontSize));
        t.setFill(Color.color(1, 1, 1, 1));
        t.setEffect(new Glow(glow));
        return t;
    }
    
    
    private void startSceneShow(){
        
        startSound.play();
        //the buttons  on the startwindow
        Text gameTitle = createText("-SPACE INVADERS-", 70, 7);
        Text startText = createText("START", 50, 3);
        Text exitText = createText("EXIT", 50, 3);
        Text optionsText = createText("OPTIONS", 50, 3);
      
        //the rocket on the both sides of each button
        Image rocket = new Image(ROCKET_IMAGE_URL);
        HBox startWithRocket = createTextWithRocket(rocket, startText);
        HBox exitWithRocket = createTextWithRocket(rocket, exitText);
        HBox optionWithRocket = createTextWithRocket(rocket, optionsText);
       
       
       addMouseHandlers(startText, exitText, optionsText);
        
       Image startground = new Image (StartBACKGROUND_IMAGE_URL);
       ImageView mv_startground = new ImageView(startground);
       mv_startground.setFitHeight(1100);
       mv_startground.setFitWidth(1100);
       mv_startground.setRotate(180);
       
       
       PathTransition path_startGround = new PathTransition(Duration.millis(9000), startGroundLine, mv_startground);
       path_startGround.setAutoReverse(false);
       path_startGround.setCycleCount(Timeline.INDEFINITE);
       path_startGround.play();
       
       //the  two line of monsters on the start window
       Image monsterImage = new Image(MONSTER_IMAGE_URL);
       ImageView[] leftMonsters = new ImageView[25];
       ImageView[] rightMonsters = new ImageView[25];

       for (int i = 0; i < 25; i++) {

            leftMonsters[i] = new ImageView(monsterImage);
            leftMonsters[i].setFitHeight(MONSTER_HEIGHT);
            leftMonsters[i].setFitWidth(MONSTER_WIDTH );

            Line leftLine = new Line(50, -30, 50, 750);
            leftTransitionMonsters = new PathTransition(Duration.millis(9000), leftLine, leftMonsters[i]);
            leftTransitionMonsters.setAutoReverse(false);
            leftTransitionMonsters.setCycleCount(Timeline.INDEFINITE);
            leftTransitionMonsters.setDelay(Duration.seconds(i * 0.5));
            leftTransitionMonsters.play();

            rightMonsters[i] = new ImageView(monsterImage);
            rightMonsters[i].setFitHeight(MONSTER_HEIGHT);
            rightMonsters[i].setFitWidth(MONSTER_WIDTH );

            Line rightLine = new Line(950, -30, 950, 750);
            rightTransitionMonsters = new PathTransition(Duration.millis(9000), rightLine, rightMonsters[i]);
            rightTransitionMonsters.setAutoReverse(false);
            rightTransitionMonsters.setCycleCount(Timeline.INDEFINITE);
            rightTransitionMonsters.setDelay(Duration.seconds(i * 0.5));
            rightTransitionMonsters.play();
       }
       
        //the action on the exit button
        exitText.setOnMousePressed(e -> { 
            startSound.stop();
            primaryStage.close();
       });

        //the action on the options button
        optionsText.setOnMousePressed(e -> { 
           rightTransitionMonsters.stop();
           leftTransitionMonsters.stop();
           path_startGround.stop();
           optionSceneShow();
       });
        
        //the action on the start button
         startText.setOnMousePressed(e -> { 
           startSound.stop();
           start.play();
           rightTransitionMonsters.stop();
           leftTransitionMonsters.stop();
           path_startGround.stop();
           signInSceneShow();
       });
       
       // monster pane in startwindow
       Pane leftPane = new Pane(leftMonsters);
       Pane rightPane = new Pane(rightMonsters);
       
       VBox buttonsbox = new VBox(10,startWithRocket, optionWithRocket, exitWithRocket);
       buttonsbox.setAlignment(Pos.CENTER);
       
       HBox hb = new HBox(gameTitle);
       hb.setAlignment(Pos.CENTER);
        
       BorderPane borderPane = new BorderPane();
       borderPane.setCenter(buttonsbox);
       borderPane.setTop(hb);
       borderPane.setPadding(new Insets(30,100,350,10));
      
       StackPane spn = new StackPane(mv_startground,leftPane,rightPane,borderPane);
       startWindowGroup.getChildren().add(spn);
       primaryStage.setTitle("Space Invaders Game");
       primaryStage.setScene(startWindowScene);
       primaryStage.setResizable(false);
       primaryStage.show();
    }
    private void optionSceneShow(){
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(SCENE_WIDTH, SCENE_HEIGHT);
        gridPane.setPadding(new Insets(70,10,10,10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Button buttonOk=new Button("Ok");
        buttonOk.setStyle("-fx-font-family: Arial; -fx-font-size:20px; -fx-background-color:DEEPSKYBLUE ;  -fx-text-fill: white; -fx-font-weight: bold");
        gridPane.add(buttonOk, 2, 2);
        buttonOk.setOnMousePressed(e -> { 
        primaryStage.setScene(startWindowScene);
       });
        
        TextField txt1 = new TextField();
        TextField txt2 = new TextField();
        gridPane.add(txt1, 1, 0);
        gridPane.add(txt2, 1, 1);
         
        Image optionGround = new Image (StartBACKGROUND_IMAGE_URL);
        ImageView imv_optionGround = new ImageView(optionGround);
        imv_optionGround.setFitHeight(1100);
        imv_optionGround.setFitWidth(1100);
        imv_optionGround.setRotate(180);
        
       
        PathTransition path_optionGround = new PathTransition(Duration.millis(9000), startGroundLine, imv_optionGround);
        path_optionGround.setAutoReverse(false);
        path_optionGround.setCycleCount(Timeline.INDEFINITE);
        path_optionGround.play();
       
       
        MenuButton menuButton = new MenuButton("Background");
        menuButton.setStyle("-fx-font-family: Arial; -fx-font-size:20px; -fx-background-color:DEEPSKYBLUE ;  -fx-text-fill: white; -fx-font-weight: bold");
        MenuItem menuItem1 =new MenuItem("Blue") ;
        MenuItem menuItem2 =new MenuItem("Space") ;
        MenuItem menuItem3 =new MenuItem("Galaxy") ;
        MenuItem menuItem4 =new MenuItem("Stars") ;
        menuButton.getItems().addAll(menuItem1,menuItem2,menuItem3,menuItem4);
        gridPane.add(menuButton, 0, 0);
        
        menuItem1.setOnAction(e ->{ txt1.setText("Blue");});
        menuItem2.setOnAction(e ->{ txt1.setText("Space");
        backgroundImageView.setImage(backgroundImage2);});
        menuItem3.setOnAction(e ->{ txt1.setText("Galaxy");
        backgroundImageView.setImage(backgroundImage3);});
        menuItem4.setOnAction(e ->{ txt1.setText("Stars");
        backgroundImageView.setImage(backgroundImage4);});

        
        MenuButton menuButton2 = new MenuButton("  Player  ");
        menuButton2.setStyle("-fx-font-family: Arial; -fx-font-size:20px; -fx-background-color:DEEPSKYBLUE ;  -fx-text-fill: white; -fx-font-weight: bold");
        MenuItem menuItem5 =new MenuItem("Bink-player") ;
        MenuItem menuItem6 =new MenuItem("orange-player") ;
        MenuItem menuItem7 =new MenuItem("defult-player") ;
        menuButton2.getItems().addAll(menuItem5,menuItem6,menuItem7);
        gridPane.add(menuButton2, 0, 1);
        
        menuItem5.setOnAction(e ->{ txt2.setText("Bink-player");
        player.setPlayerImag1();});
        menuItem6.setOnAction(e ->{ txt2.setText("orange-player");
        player.setPlayerImag2();});
        menuItem7.setOnAction(e ->{ txt2.setText("defult-player");
        player.setPlayerImag0();});
       
        Pane optionRoot = new Pane();
        optionRoot.getChildren().addAll(imv_optionGround, gridPane);
        optionGroup.getChildren().add(optionRoot);
        primaryStage.setScene(optionScene);
    }   
    
    private void signInSceneShow() {
        buttonPlayer.stop();
        Line signInGroundline=new Line(500,350,500,550);
     
        Image signInGround = new Image (StartBACKGROUND_IMAGE_URL);
        ImageView imv_signInGround = new ImageView(signInGround);
        imv_signInGround.setFitHeight(1100);
        imv_signInGround.setFitWidth(1100);
        imv_signInGround.setRotate(180);
        
        // move background
        PathTransition path_signInGround = new PathTransition(Duration.millis(9000), signInGroundline, imv_signInGround);
        path_signInGround.setAutoReverse(false);
        path_signInGround.setCycleCount(Timeline.INDEFINITE);
        path_signInGround.play();
     
        Label nameLabel = new Label("Enter your name:");
        nameLabel.setTextFill(Color.WHITE); // Set text color to WHITE
        nameLabel.setFont(Font.font("Arial", 25));
        TextField nameField = new TextField();
        Button signInButton = new Button("Sign In");
        signInButton.setStyle("-fx-font-family: Arial; -fx-font-size:20px; -fx-background-color:DEEPSKYBLUE ;  -fx-text-fill: white; -fx-font-weight: bold");
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 20px");
        
        signInButton.setOnAction(e -> {
          start.stop();
          
          path_signInGround.stop();
          playerName = nameField.getText();
            if (playerName.isEmpty()) {
            // Display warning message when name field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Empty Name Field");
            alert.setContentText("Please enter your name before signing in.");
            alert.showAndWait();}
            else{
                backgroundPlayer.play();
          gameSceneShow();}
        });
        
        Group signInGroup = new Group();
        Scene signInScene = new Scene(signInGroup,SCENE_WIDTH,SCENE_HEIGHT); 
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(nameLabel, nameField, signInButton);
      
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(250,550,250,400));
        pane.setCenter(layout);
        
        Pane signInRoot = new Pane();
        signInRoot.getChildren().addAll(imv_signInGround, pane);
        signInGroup.getChildren().add(signInRoot);
        signInGroup.setOnKeyPressed(e->{
          if(e.getCode()==KeyCode.ENTER){
              signInButton.fire();
          }
        });
        primaryStage.setScene(signInScene);
        

    }

   public void gameSceneShow() {
      
      lastHeart =2; //set lastHeart every run of gameScene
      Monsters monsters = new Monsters(5000);
      
      //gameovershap
      gameOver.setFont(Font.font("Andalus",100));
      gameOver.setStrokeWidth(100);
      gameOver.setLayoutX((SCENE_WIDTH - gameOver.getLayoutBounds().getWidth()) / 2);
      gameOver.setLayoutY((SCENE_HEIGHT - gameOver.getLayoutBounds().getHeight()) / 2);
      gameOver.setVisible(false);
      Color[] colors = {Color.AQUA, Color.RED};
       int[] colorIndex = {0};
       Timeline timelineGameover = new Timeline(
          new KeyFrame(Duration.seconds(.4), (ActionEvent event) -> {
              colorIndex[0] = (colorIndex[0] + 1) % colors.length;
              gameOver.setFill(colors[colorIndex[0]]);  
           })
        );
        timelineGameover.setCycleCount(Animation.INDEFINITE);
        timelineGameover.play();
        
      
      // endgamebuttonshap
      Label endGameButton = new Label("X");
      endGameButton.setLayoutX(SCENE_WIDTH - 50);
      endGameButton.setLayoutY(30);
      endGameButton.setStyle("-fx-background-color:DEEPSKYBLUE ; -fx-font-weight: bold; -fx-font-size: 25px; -fx-text-fill: black; -fx-border-color: black; -fx-border-width:3px; -fx-min-width: 35px; -fx-min-height: 35px; -fx-alignment: center; ");
      endGameButton.toFront();
      //
      Text scoreText=new Text();
      Label lblScore = new Label();
      lblScore.setGraphic(scoreText);
      scoreText.setFill(Color.DEEPSKYBLUE);
      scoreText.setFont(Font.font("Andalus", 30));
      scoreText.setStroke(Color.BLACK);
      scoreText.setStrokeWidth(1.4);
      scoreText.setStrokeType(StrokeType.OUTSIDE);
      lblScore.setLayoutX(0);
      lblScore.setLayoutY(60);
      gameRoot.getChildren().addAll(backgroundImageView, player, monsters,lblScore,endGameButton,gameOver);
      
      // move background
      AnimationTimer gameGroundTime = new AnimationTimer() {
         @Override
         public void handle(long now) {
             backgroundPosition += BACKGROUND_SPEED; 
             if (backgroundPosition ==0) {
                 backgroundPosition = -BACKGROUND_HEIGHT; // Reset the image when it goes beyond the screen
               }
             Platform.runLater(()->{
                 backgroundImageView.setLayoutY(backgroundPosition);
                }); 
            }
        };
      gameGroundTime.start();
     
      Timeline bulletUpdateTime=new Timeline(new KeyFrame(Duration.millis(1),e->{
           List<Bullet> toRemove = new ArrayList<>();
           for (Bullet bullet : bullets) {
              bullet.updatePosition();
              if (!bullet.isVisible()) {
                 toRemove.add(bullet);
                }
            }
            bullets.removeAll(toRemove);
            gameRoot.getChildren().removeAll(toRemove);
            score= monsters.getScore();

            monsters.checkCollisions(bullets);
        }));
      bulletUpdateTime.setCycleCount(-1);
      bulletUpdateTime.play();
      

      
      heartTime = new Timeline(new KeyFrame(Duration.seconds(1),e->{
          Platform.runLater(()->scoreText.setText("Score: " + monsters.getScore())); // to make the game runfaster  
          if (isCollision(player, monsters)) {
               remove.stop();
               remove.play();
               gameRoot.getChildren().removeAll(monsters,endGameButton);
               PauseTransition pause_collision = new PauseTransition(Duration.seconds(0.1));
               pause_collision.setOnFinished(event -> {
                 gameRoot.getChildren().remove(heartList.get(lastHeart));
                 heartList.remove(lastHeart);
                 gameRoot.getChildren().addAll(monsters,endGameButton);
                 
                 lastHeart--;
                });
               pause_collision.play();
            }
            if (heartList.isEmpty()){
                remove.stop();
                backgroundPlayer.stop();
                lose.play();
                
                gameOver.setVisible(true);
                gameGroundTime.stop();
                bulletUpdateTime.stop();
                gameRoot.getChildren().removeAll( player, monsters,endGameButton,lblScore);    
                PauseTransition pause_gameover = new PauseTransition(Duration.seconds(2));
                pause_gameover.setOnFinished(event -> {
                  gameOver.setVisible(false);
                  timelineGameover.stop();
                  gameRoot.getChildren().removeAll(backgroundImageView,gameOver);
                  showScore();
                  
                });
                pause_gameover.play();
            }
        
        
        }));
        heartTime.setCycleCount(-1);
        heartTime.play();
        endGameButton.setOnMouseClicked(e -> {
          gameRoot.getChildren().removeAll(backgroundImageView, player, monsters,endGameButton,lblScore,gameOver);
          
          gameRoot.getChildren().remove(heartList);
          heartList.clear();
          gameGroundTime.stop();
          bulletUpdateTime.stop();
          timelineGameover.stop();
          
          showScore();
        });
       
       // Setup key event handling on the gamescene
       gameScene.setOnKeyPressed(e -> {
          
          laser.stop();
          player.checkBorder();
          if (e.getCode() == RIGHT && !player.checkBorder_right) {
              player.setX(20);
          } else if (e.getCode() == LEFT && !player.checkBorder_left) {
              player.setX(-20);
          } else if (e.getCode() == SPACE) {
              laser.play();
              Bullet bullet = new Bullet(player.getX(), player.getY());
              gameRoot.getChildren().add(bullet);
              bullets.add(bullet);
            }
        });
        
        setDefultHeart();
        
        primaryStage.setScene(gameScene);
        gameRoot.requestFocus(); // Important to set focus to receive key events
    }   


    private void showScore() {
       remove.stop();
       lose.stop();
       backgroundPlayer.stop();
       heartTime.stop();
       
       // showScoreground setting
       Image showScoreground = new Image (StartBACKGROUND_IMAGE_URL);
       ImageView imv_showScoreground = new ImageView(showScoreground);
       imv_showScoreground.setFitHeight(1100);
       imv_showScoreground.setFitWidth(1100);
       imv_showScoreground.setRotate(180);
       
        // nameLabel of score setting
        Label scoreLabel = new Label("Score for " + playerName + ": " + score);
        scoreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 25px");
        
        Label message = new Label("Don't Lose Hope! Continue The Fun"); 
        message.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 20px");
        
        Button closeButton = new Button(" Go To Start Window");
        closeButton.setStyle("-fx-font-family: Arial; -fx-font-size:20px; -fx-background-color:DEEPSKYBLUE ;  -fx-text-fill: white; -fx-font-weight: bold");
        
        
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(scoreLabel,message, closeButton);
        
        StackPane score = new StackPane();
        score.getChildren().addAll(imv_showScoreground,layout);

        scoreScene = new Scene(score, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scoreScene);
        closeButton.setOnAction(e -> {
            buttonPlayer.play();
            startSceneShow();
        });
    }
    
    // collision between player & monster
    private boolean isCollision(Player player, Monsters monsters) {
        Bounds playerBounds = player.getPlayerBounds();
        for (Node monster : monsters.getChildren()) {
            if (playerBounds.intersects(monster.getBoundsInParent())) { // check if boundbox of player interset boundbox of monster
                 monsters.getChildren().remove(monster);
                return true; 
           }
        }
        return false; 
    }
     
    private void setDefultHeart(){
        for(int i=0;i<num_hearts;i++){
            Heart heart =new Heart();
            heart.setX(i*50);
            heart.setY(10);
            heartList.add(heart);
            gameRoot.getChildren().add(heart);
        }
        
    }
    private void displayCounter(int count, Pane root) {
    Text counterText = new Text(Integer.toString(count));
    counterText.setFont(Font.font("Andalus", 200));
    counterText.setFill(Color.WHITE);
    counterText.setLayoutX((SCENE_WIDTH + counterText.getLayoutBounds().getWidth()-200) /2);
    counterText.setLayoutY((SCENE_HEIGHT + counterText.getLayoutBounds().getHeight()-200) /2);
    root.getChildren().add(counterText);
    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), counterText);
    fadeOut.setFromValue(1);
    fadeOut.setByValue(0.1);
    fadeOut.setToValue(0);
    fadeOut.setOnFinished(e -> root.getChildren().remove(counterText));
    fadeOut.play();
    
    
}
    
    public static void main(String[] args) {
        launch(args);
    }
  
}
