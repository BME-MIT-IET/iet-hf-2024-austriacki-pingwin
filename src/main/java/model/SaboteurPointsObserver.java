package model;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Szoftver projekt laboratórium: Sivatagi vízhálózat
//  @ File Name : Model.SaboteurPointsObserver.java
//  @ Date : 2023. 03. 28.
//  @ Author : aD4B team
//
//


import org.apache.logging.log4j.Logger;
import proto.LogHelper;

/**
 * Felügyeli a sivatagba folyt vizet.
 */
public class SaboteurPointsObserver extends Observer {
	private static final Logger LOGGER = LogHelper.getLogger();

	private int desertWater = 0;	//Mennyi víz folyt ki a sivatagba.

	/**
	 * Frissíti a sivatagba folyt víz mennyiségét.
	 */
	public void update(int water) {
		LOGGER.debug(water+" mennyíségű víz a sivatagi tárolóba került");
		desertWater += water;
		LOGGER.debug(desertWater+" mennyíségű víz a van a sivatagi tárolóban");
	}

	public int getDesertWater() { return desertWater; }

}