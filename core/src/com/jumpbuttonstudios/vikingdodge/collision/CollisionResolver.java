package com.jumpbuttonstudios.vikingdodge.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.entity.FireBall;
import com.jumpbuttonstudios.vikingdodge.entity.Player;
import com.jumpbuttonstudios.vikingdodge.entity.Rock;
import com.jumpbuttonstudios.vikingdodge.entity.Sheep;
import com.jumpbuttonstudios.vikingdodge.entity.ThrownSheep;
import com.jumpbuttonstudios.vikingdodge.entity.environment.Border;
import com.jumpbuttonstudios.vikingdodge.entity.environment.Ground;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.event.EventTriggerer;

public class CollisionResolver implements ContactListener, EventTriggerer {

	VikingDodge vikingDodge;

	/** All listeners waiting for contacts */
	Array<EventListener> listeners = new Array<EventListener>();

	int footContacts = 0;

	public CollisionResolver(VikingDodge vikingDodge) {
		addEventListener(vikingDodge);
		this.vikingDodge = vikingDodge;
	}

	@Override
	public void beginContact(Contact contact) {
		Object objectA = contact.getFixtureA().getBody().getUserData();
		Object objectB = contact.getFixtureB().getBody().getUserData();

		if (objectA instanceof Ground && objectB instanceof Sheep) {
			notify(null, Event.SHEEP_ON_GROUND);
		}
		if (objectB instanceof Ground && objectA instanceof Sheep) {
			notify(null, Event.SHEEP_ON_GROUND);
		}
		if (objectB instanceof Player && objectA instanceof Sheep) {
			notify((Entity) objectA, Event.PLAYER_COLLIDED_WITH_SHEEP);
		}
		if (objectA instanceof Player && objectB instanceof Sheep) {
			notify((Entity) objectB, Event.PLAYER_COLLIDED_WITH_SHEEP);
		}
		if (objectA instanceof Rock && objectB instanceof Ground) {
			notify((Entity) objectA, Event.ROCK_COLLIDED_WITH_GROUND);
		}
		if (objectB instanceof Rock && objectA instanceof Ground) {
			notify((Entity) objectB, Event.ROCK_COLLIDED_WITH_GROUND);
		}
		if (objectA instanceof Rock && objectB instanceof Player) {
			notify((Entity) objectB, Event.PLAYER_COLLIDED_WITH_ROCK);
		}
		if (objectA instanceof Player && objectB instanceof Rock) {
			notify((Entity) objectB, Event.PLAYER_COLLIDED_WITH_ROCK);
		}
		if (objectA instanceof Rock && objectB instanceof Rock) {
			Rock rock1 = (Rock) objectA;
			Rock rock2 = (Rock) objectB;
			
			if (rock1.getPhysics().getBody().getPosition().y > rock2
					.getPhysics().getBody().getPosition().y) {
				notify(rock1, Event.ROCK_COLLIDED_WITH_GROUND);
			} else {
				notify(rock2, Event.ROCK_COLLIDED_WITH_GROUND);
			}
			
		}
		
		if (objectA instanceof Rock && objectB instanceof Rock) {
			Rock rock1 = (Rock) objectA;
			Rock rock2 = (Rock) objectB;
			
			if (rock1.getPhysics().getBody().getPosition().y > rock2
					.getPhysics().getBody().getPosition().y) {
				notify(rock1, Event.ROCK_COLLIDED_WITH_GROUND);
			} else {
				notify(rock2, Event.ROCK_COLLIDED_WITH_GROUND);
			}
		}

		if (objectA instanceof ThrownSheep
				&& (objectB instanceof Rock || objectB instanceof Ground || objectB instanceof Border)) {
			notify((Entity) objectA, Event.THROWN_SHEEP_COLLISION);
		} else if (objectB instanceof ThrownSheep
				&& (objectA instanceof Rock || objectA instanceof Ground || objectA instanceof Border)) {
			notify((Entity) objectB, Event.THROWN_SHEEP_COLLISION);
		} else if (objectA instanceof Rock && objectB instanceof Sheep) {
			notify((Entity) objectB, Event.ROCK_COLLIDED_WITH_SHEEP);
			notify((Entity) objectA, Event.ROCK_COLLIDED_WITH_SHEEP);
			contact.setEnabled(false);
		} else if (objectA instanceof Sheep && objectB instanceof Rock) {
			notify((Entity) objectA, Event.ROCK_COLLIDED_WITH_SHEEP);
			notify((Entity) objectB, Event.ROCK_COLLIDED_WITH_SHEEP);
			contact.setEnabled(false);
		}

		if (objectA instanceof FireBall
				&& (objectB instanceof Player || objectB instanceof Ground || objectB instanceof Border)) {
			notify((Entity) objectA, Event.FIREBALL_COLLISION);
		}
		if (objectB instanceof FireBall
				&& (objectA instanceof Player || objectA instanceof Ground || objectA instanceof Border)) {
			notify((Entity) objectB, Event.FIREBALL_COLLISION);
		}
		if (objectA instanceof FireBall && objectB instanceof Ground) {
			notify((Entity) objectA, Event.FIREBALL_GROUND_COLLISION);
		}
		if (objectB instanceof FireBall && objectA instanceof Ground) {
			notify((Entity) objectB, Event.FIREBALL_GROUND_COLLISION);
		}
		if (objectA instanceof Player && objectB instanceof FireBall) {
			notify((Entity) objectA, Event.PLAYER_COLLIDED_WITH_FIREBALL);
		}
		if (objectB instanceof Player && objectA instanceof FireBall) {
			notify((Entity) objectB, Event.PLAYER_COLLIDED_WITH_FIREBALL);
		}
		if (objectA instanceof Sheep && objectB instanceof FireBall) {
			notify((Entity) objectA, Event.SHEEP_COLLIDED_WITH_FIREBALL);
			System.out.println("here");
		}
		if (objectB instanceof Sheep && objectA instanceof FireBall) {
			notify((Entity) objectB, Event.SHEEP_COLLIDED_WITH_FIREBALL);
			System.out.println("here");
		}

		if (contact.getFixtureB().getUserData() != null) {
			if (contact.getFixtureA().getUserData() instanceof Player) {
				if (footContacts == 0)
					notify(null, Event.PLAYER_ON_TOP_OF_SOMETHING);
				footContacts++;
			}
		}
		if (contact.getFixtureA().getUserData() != null) {
			if (contact.getFixtureA().getUserData() instanceof Player) {
				if (footContacts == 0)
					notify(null, Event.PLAYER_ON_TOP_OF_SOMETHING);
				footContacts++;

			}
		}

	}

	@Override
	public void endContact(Contact contact) {

		if (contact.getFixtureB() != null)
			if (contact.getFixtureB().getUserData() != null) {
				if (contact.getFixtureA().getUserData() instanceof Player) {
					footContacts--;
				}
			}
		if (contact.getFixtureA() != null)
			if (contact.getFixtureA().getUserData() != null) {
				if (contact.getFixtureA().getUserData() instanceof Player) {
					footContacts--;

				}
			}

		if (footContacts == 0)
			notify(null, Event.PLAYER_FELL);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

	@Override
	public void notify(Entity entity, Event event) {
		for (EventListener eventListener : listeners) {
			eventListener.onNotify(entity, event);
		}
	}

	@Override
	public void addEventListener(EventListener eventListener) {
		listeners.add(eventListener);
	}

	@Override
	public void removeEventListener(EventListener eventListener) {

	}

}
