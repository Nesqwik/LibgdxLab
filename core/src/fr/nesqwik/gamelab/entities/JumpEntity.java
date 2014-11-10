package fr.nesqwik.gamelab.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import fr.nesqwik.gamelab.GameLab;

public class JumpEntity extends Entity {
	private static int MAX_FALL_SPEED = 500;
	private static int JUMP_HIGHT = 100;
	
	private boolean canJump = true;
	
	public JumpEntity(GameLab game) {
		super(game);
		texture = new Texture("ball.png");
		sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
		rectangle = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
	}
	
	@Override
	public void update(float delta) {
		speed.x = 0;
		speed.y += GameLab.GRAVITY;
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) speed.x = -200;
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) speed.x = 200;
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			speed.y += -JUMP_HIGHT;
			//System.out.println(speed.y);
		}
		// cap speed Y
		if (speed.y >= MAX_FALL_SPEED) speed.y = MAX_FALL_SPEED;
		if (speed.y <= -MAX_FALL_SPEED) speed.y = -MAX_FALL_SPEED;
		moveWithCollision(delta);
		checkCollides();
	}

	@Override
	protected void collideWith(Entity e) {
		System.out.println(e);
	}

}
