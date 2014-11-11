package fr.nesqwik.gamelab.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import fr.nesqwik.gamelab.GameLab;

public class StaticBlockEntity extends Entity {

	public StaticBlockEntity(GameLab game, Vector2 pos) {
		super(game);
		texture = new Texture("ground.png");
		sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
		rectangle.set(0, 0, texture.getWidth(), texture.getHeight());
		setPosition(pos);
		
	}

	@Override
	public void update(float delta) {
	}

	@Override
	protected void collideXWith(Entity e) {
	}
	
	@Override
	protected void collideYWith(Entity e) {
	}
}
