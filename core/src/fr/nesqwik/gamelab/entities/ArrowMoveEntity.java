package fr.nesqwik.gamelab.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import fr.nesqwik.gamelab.GameLab;

public class ArrowMoveEntity extends Entity {

	public ArrowMoveEntity(GameLab game) {
		super(game);
		texture = new Texture("ball.png");
		sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
		rectangle = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
	}
	
	@Override
	public void update(float delta) {
		speed.x = 0;
		speed.y = 0;
		if(Gdx.input.isKeyPressed(Keys.LEFT)) speed.x = -100.5f;
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) speed.x = 100.5f;
		if(Gdx.input.isKeyPressed(Keys.UP)) speed.y = -100.5f;
		if(Gdx.input.isKeyPressed(Keys.DOWN)) speed.y = 100.5f;
		moveWithCollision(delta);
		checkCollides();
	}

	@Override
	protected void collideWith(Entity e) {
		System.out.println(e);
	}

}
