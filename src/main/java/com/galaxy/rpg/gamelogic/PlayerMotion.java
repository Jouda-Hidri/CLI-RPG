package com.galaxy.rpg.gamelogic;

public interface PlayerMotion<T> {

	public void create();

	public void move();

	public void fight(T player);

	public void reduceHealth(T player);

}
