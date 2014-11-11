package fr.nesqwik.gamelab.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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

	/**
	 * test if entities are overlapsing or touching each other
	 * @param that
	 * @return true if is colliding, false else
	 */
	public boolean isCollideWith(Entity that) {
		return this.rectangle.overlaps(incrRect(that.rectangle));
	}

	/**
	 * Increments rect by 1 in 4 directions.
	 * @return new incremented rectangle
	 */
	private Rectangle incrRect(Rectangle rect) {
		return new Rectangle(rect.x - 1, rect.y - 1, rect.width + 2, rect.height + 2);
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

	/**
	 * Déplace l'objet de x pixel en x et y pixel en y.
	 * @param delta
	 */
	public void move(float delta, float x, float y) {
		setPosition((getPosition().x + x*delta), (getPosition().y + y*delta));
	}

	public void moveWithCollision(float delta) {
		moveWithCollisionX(delta, speed.x);
		moveWithCollisionY(delta, speed.y);
	}

	public void moveWithCollisionX(float delta, float mx) {
		if(Math.abs(mx) <= rectangle.width) {
			// Move X
			float moveX = getAffineMoveX(delta, mx);
			move(delta, moveX, 0);
			if(moveX != mx) checkCollidesX();
		} else {
			moveWithCollisionX(delta, mx/2);
			moveWithCollisionX(delta, (mx+1)/2);
		}
	}


	public void moveWithCollisionY(float delta, float my) {
		if(Math.abs(my) <= rectangle.height) {
			// Move X
			float moveY = getAffineMoveY(delta, my);
			move(delta, 0, moveY);
			if(moveY != my) checkCollidesY();
		} else {
			moveWithCollisionY(delta, my/2);
			moveWithCollisionY(delta, (my+1)/2);
		}
	}



	private void checkCollidesY() {
		Array<Entity> entities = game.getEntities();
		for(int i = 0 ; i < entities.size ; i++) {
			if(!this.equals(entities.get(i)) && this.isCollideWith(entities.get(i)))
				this.collideYWith(entities.get(i));
		}
	}

	private void checkCollidesX() {
		Array<Entity> entities = game.getEntities();
		for(int i = 0 ; i < entities.size ; i++) {
			if(!this.equals(entities.get(i)) && this.isCollideWith(entities.get(i)))
				this.collideXWith(entities.get(i));
		}
	}

	private float getAffineMoveX(float delta, float mx) {
		float affineSpeedX = mx;

		// affine Y
		while(affineSpeedX != 0 && !tryToMoveX(affineSpeedX, delta)) {
			// reset theoric position to current position
			// reduce the maxSpeed by 1
			if (mx > 0) {
				affineSpeedX -= 1f;
			} else {
				affineSpeedX += 1f;
			}
		}
		return affineSpeedX;
	}

	private float getAffineMoveY(float delta, float my) {
		float affineSpeedY = my;

		// affine Y
		while(affineSpeedY != 0 && !tryToMoveY(affineSpeedY, delta)) {
			// reset theoric position to current position
			// reduce the maxSpeed by 1
			if (my > 0) {
				affineSpeedY -= 1f;
			} else {
				affineSpeedY += 1f;
			}
		}
		return affineSpeedY;
	}


	private boolean tryToMoveX(float x, float delta) {
		Rectangle theoricRect = new Rectangle(	getPosition().x + x * delta,
				getPosition().y,
				rectangle.width, rectangle.height);
		return !isColliding(theoricRect);
	}

	private boolean tryToMoveY(float y, float delta) {
		Rectangle theoricRect = new Rectangle(	getPosition().x,
				getPosition().y + y * delta,
				rectangle.width, rectangle.height);
		return !isColliding(theoricRect);
	}

	protected abstract void collideXWith(Entity e);
	protected abstract void collideYWith(Entity e);
	public abstract void update(float delta);
}
