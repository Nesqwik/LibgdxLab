package fr.nesqwik.gamelab.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import fr.nesqwik.gamelab.GameLab;

public abstract class Entity implements Disposable {
	public static final int MAX_PIX_BY_STEP = 10;
	private GameLab game;

	protected Texture texture;
	protected Sprite sprite;
	protected Rectangle rectangle = new Rectangle();

	protected Vector2 speed = new Vector2();
	protected Vector2 theoricPos = new Vector2();

	protected float rotationSpeed;

	public Entity(GameLab game) {
		this.game = game;
	}

	public void draw(SpriteBatch batch) {
		//batch.draw(img, pos.x - img.getWidth()/2, pos.y - img.getWidth()/2, img.getWidth()/2, img.getHeight()/2, img.getWidth(), img.getHeight(), 1, 1, angle, 0, 0, img.getWidth(), img.getHeight(), false, false);
		sprite.draw(batch);
	}

	public void setAngle(float angle) {
		sprite.setRotation(angle);
	}

	public void addAngle(float angle) {
		sprite.setRotation((sprite.getRotation() + angle) % 360);
	}

	public void setPosition(Vector2 pos) {
		setPosition(pos.x, pos.y);
	}

	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
		rectangle.x = x;
		rectangle.y = y;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}

	public Vector2 getPosition() {
		return new Vector2(sprite.getX(), sprite.getY());
	}

	public void dispose() {
		texture.dispose();
	}

	public boolean isCollideWith(Entity that) {
		return this.rectangle.overlaps(that.rectangle);
	}

	public void checkCollides() {
		Array<Entity> entities = game.getEntities();
		for(int i = 0 ; i < entities.size ; i++) {
			if(!this.equals(entities.get(i)) && this.isCollideWith(entities.get(i)))
				this.collideWith(entities.get(i));
		}
	}
	
	public boolean isColliding(Rectangle theoricRect) {
		Array<Entity> entities = game.getEntities();
		for(int i = 0 ; i < entities.size ; i++) {
			if(!this.equals(entities.get(i)) 
					&& theoricRect.overlaps(entities.get(i).rectangle)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Déplace l'objet de speed.x pixel en x et speed.y pixel en y.
	 * @param delta
	 */
	public void move(float delta) {
		setPosition((getPosition().x + speed.x*delta), (getPosition().y + speed.y*delta));
	}

	public void moveWithCollision(float delta) {
		Vector2 affineSpeed = new Vector2();
		affineSpeed.x = speed.x * delta;
		affineSpeed.y = speed.y * delta;
		
		theoricPos = getPosition();
		// affine X
		

		while(affineSpeed.x != 0 && !tryToMoveX(affineSpeed)) {
			// reset theoric position to current position
			theoricPos = getPosition();
			// reduce the maxSpeed by 1
			if (speed.x > 0) {
				affineSpeed.x -= 1f * delta;
			} else {
				affineSpeed.x += 1f * delta;
			}
		}
		//if(Math.abs(affineSpeed.x) < 1f) affineSpeed.x = 0;

		theoricPos = getPosition();
		// affine Y
		while(affineSpeed.y != 0 && !tryToMoveY(affineSpeed)) {
			// reset theoric position to current position
			theoricPos = getPosition();
			// reduce the maxSpeed by 1
			if (speed.y > 0) {
				affineSpeed.y -= 1f;
			} else {
				affineSpeed.y += 1f;
			}
		}
		//if(Math.abs(affineSpeed.y) < 1f) affineSpeed.y = 0;
		
		speed.x = affineSpeed.x/delta;
		speed.y = affineSpeed.y/delta;
		move(delta);
	}

	private boolean tryToMoveX(Vector2 maxSpeed) {
		Rectangle theoricRect = new Rectangle(	theoricPos.x + maxSpeed.x,
												theoricPos.y,
												rectangle.width, rectangle.height);
		return !isColliding(theoricRect);
	}
	
	private boolean tryToMoveY(Vector2 maxSpeed) {
		Rectangle theoricRect = new Rectangle(	theoricPos.x,
												theoricPos.y + maxSpeed.y,
												rectangle.width, rectangle.height);
		return !isColliding(theoricRect);
	}

	protected abstract void collideWith(Entity e);
	public abstract void update(float delta);
}
