package com.jumpbuttonstudios.vikingdodge.entity.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.interfaces.Updatable;

public class Cloud extends Sprite implements Updatable {

	public Cloud(Texture texture, float x, float y) {
		super(texture);
		setPosition(x, MathUtils.random(1.5f, 3f));
		setSize(getWidth() * VikingDodge.SCALE, getHeight() * VikingDodge.SCALE);
	}

	@Override
	public void update(float delta) {
		setPosition(getX() + 0.25f * delta, getY());
		if(getX() > VikingDodge.WIDTH)
			setPosition(0 - getWidth(), MathUtils.random(1.5f, 3f));
	}

}
