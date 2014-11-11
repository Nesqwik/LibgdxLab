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
		texture = new Texture("ground.png");
		sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
		rectangle = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
	}
	
	@Override
	public void update(float delta) {
		speed.x = 0;
		speed.y = 0;
		if(Gdx.input.isKeyPressed(Keys.LEFT)) speed.x = -200;
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) speed.x = 200;
		if(Gdx.input.isKeyPressed(Keys.UP)) speed.y = -200;
		if(Gdx.input.isKeyPressed(Keys.DOWN)) speed.y = 200;
		moveWithCollision(delta);
	}

	@Override
	protected void collideXWith(Entity e) {
		System.out.println("X : " + e.getPosition());
	}
	
	@Override
	protected void collideYWith(Entity e) {
		System.out.println("Y : " + e.getPosition());
	}

}
