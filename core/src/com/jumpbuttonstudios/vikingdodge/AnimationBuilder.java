package com.jumpbuttonstudios.vikingdodge;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationBuilder {

	public static <T> Animation create(AssetDescriptor<T> asset,
			float duration, int cols, int rows, int[] blankFrames) {
		/* The spritesheet to work with */
		Texture sheet = (Texture) Assets.get(asset);
		/* Each key frame */
		Array<Sprite> frames = new Array<Sprite>();

		/* Array of regions to be split into frames */
		TextureRegion[][] region = TextureRegion.split(sheet, sheet.getWidth()
				/ cols, sheet.getHeight() / rows);

		/* Create frames from the split texture */
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				Sprite sprite = new Sprite(region[j][i]);
				sprite.setSize(sprite.getWidth() * VikingDodge.SCALE, sprite.getHeight() * VikingDodge.SCALE);
				frames.add(sprite);

			}
		}

		/* Remove blank frames */
		if (blankFrames != null)
			for (int blank = 0; blank < blankFrames.length; blank++) {
				frames.removeIndex(blankFrames[blank]);
			}

		return new Animation(duration, frames);
	}

}
