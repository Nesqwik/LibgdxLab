package fr.nesqwik.gamelab.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;

import fr.nesqwik.gamelab.GameLab;

public class StaticBlockEntity extends Entity {

	public StaticBlockEntity(GameLab game, Vector2 pos) {
		super(game);
		texture = new Texture("ball.png");
		sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
		setPosition(pos);
		rectangle.set(pos.x, pos.y, texture.getWidth(), texture.getHeight());
	}

	@Override
	public void update(float delta) {
		checkCollides();
	}

	@Override
	protected void collideWith(Entity e) {
	}
}
