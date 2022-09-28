package application;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;

public class PongGame extends Application {


        private static final int width = 800;
        private int scoreP1 = 0;
        private int scoreP2 = 0;
        private static final int height = 600;
        private static final int PLAYER_HEIGHT = 100;
        private static final int PLAYER_WIDTH = 15;
        private static final double BALL_R = 15;
        private int ballYSpeed = 5;
        private int ballXSpeed = 5;
        private double pOneYPos = height / 2;
        private double pTwoYPos = height / 2;
        private double ballXPos = width / 2;
        private double ballYPos = height / 2;
        private boolean gameStarted;
        private int pOneXPos = 0;
        private double pTwoXPos = width - PLAYER_WIDTH;

        public void start(Stage stage) throws Exception {

            stage.setTitle("THE PONG");
            stage.setResizable(false);
            Canvas canvas = new Canvas(width, height);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
            tl.setCycleCount(Timeline.INDEFINITE);
            Image img = new Image("https://cdn.dribbble.com/users/2624732/screenshots/6670330/pingpong_lf_icon_showcase.png?compress=1&resize=400x300&vertical=top");
            stage.getIcons().add(img);
            gc.setLineWidth(1.5);
            stage.setScene(new Scene(new StackPane(canvas)));
            canvas.setOnMouseClicked(e ->  gameStarted = true);
           canvas.setOnMouseMoved(e ->  pOneYPos  = e.getY());
//            canvas.setFocusTraversable(true);
//            canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
//                @Override
//                public void handle(KeyEvent keyEvent) {
//                    switch (keyEvent.getCode())
//                    {
//                        case UP:
//                            if((pOneYPos-PLAYER_HEIGHT/4>=0)){
//                                pOneYPos-=20;
//                            }
//
//                            break;
//                        case DOWN:
//                            if((pOneYPos+PLAYER_HEIGHT<height)){
//                                pOneYPos+=20;
//
//                            }
//                            break;
////                        case LEFT:
////                            pOneXPos-=30;
////                            break;
////                        case RIGHT:
////                            pOneXPos+=30;
////                            break;
//                    }
//                }
//            });

            stage.show();
            tl.play();
        }

        private void run(GraphicsContext gc) {
//            Image imps=new Image("https://yorkdojo.github.io/worksheets/scratch/pong/background.png");
            gc.setFill(Color.AQUAMARINE);
            gc.fillRect(0, 0, width, height);
//        gc.drawImage(imps,0,0,width,height);
          gc.setFill(Color.ORANGE);
            gc.setFont(Font.font(25));

            if(gameStarted) {
                ballXPos+=ballXSpeed;
                ballYPos+=ballYSpeed;
//                    pTwoYPos = ballYPos - PLAYER_HEIGHT / 2;

                if(ballXPos < width - width  / 9) {
                    pTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
                }  else {
                    pTwoYPos =  ballYPos > pTwoYPos + PLAYER_HEIGHT / 2 ?  pTwoYPos +1 :  pTwoYPos -1;

                }
                gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
            } else {
                gc.setStroke(Color.BLACK);
                gc.setTextAlign(TextAlignment.CENTER);
                gc.strokeText("Lets        Start", width / 2, height / 2);
                int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
                ballYSpeed=5;
                ballXSpeed=5;

                ballYSpeed*=randomNum==0?-1:1;
                ballXSpeed*=randomNum==0?-1:1;;
                ballXPos = width / 2;
                ballYPos = height / 2;

            }

            if(ballYPos >= height || ballYPos <= 0) {
                ballYSpeed *=-1;
                ballXSpeed += 1 * Math.signum(ballXSpeed);

            }

            if(ballXPos < pOneXPos - PLAYER_WIDTH+2) {
                scoreP2++;
                gameStarted = false;

            }

            if(ballXPos > pTwoXPos + PLAYER_WIDTH+2) {
                scoreP1++;
                gameStarted = false;

            }
            gc.fillRoundRect(pTwoXPos, pTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT,20,20);
            gc.fillRoundRect(pOneXPos, pOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT,20,20);
            gc.fillRect(width/2, 0, PLAYER_WIDTH/2, height);


            if( ((ballXPos + BALL_R >= pTwoXPos) && ballYPos >= pTwoYPos && ballYPos <= pTwoYPos + PLAYER_HEIGHT) ||
                    ((ballXPos <= pOneXPos + PLAYER_WIDTH) && ballYPos >= pOneYPos && ballYPos <= pOneYPos + PLAYER_HEIGHT)) {

                ballYSpeed += 1 * Math.signum(ballYSpeed);
                ballXSpeed += 1 * Math.signum(ballXSpeed);

                ballXSpeed *= -1;
                ballYSpeed*=-1;



            }
            if(scoreP1==2){
                PauseTransition delay = new PauseTransition(Duration.seconds(2000));
                gc.strokeText("Win 1", width / 2, height / 2);
                delay.setOnFinished(e ->gc.strokeText("Lets        Start", width / 2, height / 2));
                delay.play();
                scoreP2=0;
                scoreP1=0;
            }
            if(scoreP2==2){
                PauseTransition delay = new PauseTransition(Duration.seconds(2000));
                gc.strokeText("Win 2", width / 2, height / 2);
                delay.setOnFinished(e ->gc.strokeText("Lets        Start", width / 2, height / 2));
                delay.play();
                scoreP2=0;
                scoreP1=0;
            }
              gc.fillText(scoreP1 + "             " + scoreP2, width / 2, 50);


        }

        public static void main(String[] args) {
            launch(args);
        }



}