package fr.nesqwik.gamelab.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import fr.nesqwik.gamelab.GameLab;

public class JumpEntity extends Entity {
	private static final int MAX_FALL_SPEED = 500;
	private static final int JUMP_HIGHT = 350;
	
	private boolean canJump = false;
	
	public JumpEntity(GameLab game) {
		super(game);
		texture = new Texture("ball.png");
		sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
		rectangle = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
		setPosition(50, 250);
	}
	
	@Override
	public void update(float delta) {
		speed.y += GameLab.GRAVITY;
		speed.x = 0;
		if(Gdx.input.isKeyPressed(Keys.LEFT)) speed.x = -200;
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) speed.x = 200;
		if(canJump && Gdx.input.isKeyPressed(Keys.SPACE)) {
			speed.y = -JUMP_HIGHT;
			canJump = false;
		}
		// cap speed Y
		if (speed.y >= MAX_FALL_SPEED) speed.y = MAX_FALL_SPEED;
		if (speed.y <= -MAX_FALL_SPEED) speed.y = -MAX_FALL_SPEED;
		moveWithCollision(delta);
	}

	@Override
	protected void collideYWith(Entity e) {
		if(speed.y > 0) {
			canJump = true;
		}
		speed.y = 0;
	}

	@Override
	protected void collideXWith(Entity e) {
		
	}

}
