package brickbreaker.main.objects.bricks;

import brickbreaker.main.components.BoxCollider;
import brickbreaker.main.components.CircleCollider;
import brickbreaker.main.scenes.SingleplayerScene;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public final class BrickManager {
    private static final ArrayList<Brick> bricks = new ArrayList<>();
    private static SingleplayerScene main;

    private BrickManager() {

    }

    public static void initializeBricks(PooledEngine engine, SingleplayerScene main) {
        BrickManager.main = main;

        for(int i=0; i<10; i++) {
            bricks.add(new Brick(engine, new Vector2((Gdx.graphics.getWidth()/10f)*i, Gdx.graphics.getHeight()/1.04499274311f)));
        }

        for(int i=0; i<10; i++) {
            bricks.add(new Brick(engine, new Vector2((Gdx.graphics.getWidth()/10f)*i, Gdx.graphics.getHeight()/1.04499274311f-30)));
        }

        for(int i=0; i<10; i++) {
            bricks.add(new Brick(engine, new Vector2((Gdx.graphics.getWidth()/10f)*i, Gdx.graphics.getHeight()/1.04499274311f-60)));
        }
    }

    public static void renderBricks() {
        for (int i = 0; i < bricks.size(); i++) {
            bricks.get(i).render();

            if (bricks.get(i).getComponent(BoxCollider.class).checkWith(main.ball.getComponent(CircleCollider.class))) {
                bricks.remove(bricks.get(i));

                main.ballDirection = -1;
                main.score++;
                main.scoreLabel.setText(String.format("Score: %d", main.score));

                main.sound.play(1);
            }
        }

        if(bricks.isEmpty()) System.out.println("You win");
    }
}