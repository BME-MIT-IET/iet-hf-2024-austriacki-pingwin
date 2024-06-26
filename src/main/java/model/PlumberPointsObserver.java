package model;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Szoftver projekt laboratórium: Sivatagi vízhálózat
//  @ File Name : Model.PlumberPointsObserver.java
//  @ Date : 2023. 03. 28.
//  @ Author : aD4B team
//
//


import org.apache.logging.log4j.Logger;
import proto.LogHelper;

/**
 * Felügyeli a Ciszternákba folyt víz mennyiségét.
 */
public class PlumberPointsObserver extends Observer {
	private static final Logger LOGGER = LogHelper.getLogger();

	private int cisternWater = 0;		//mennyi víz folyt a ciszternákba

	/**
	 * Frissíti a ciszternákba folyt víz mennyiségét.
	 */
	public void update(int water) {
		LOGGER.debug(water+" mennyíségű víz a ciszterna tárolóba került");
		cisternWater += water;
	}

	public int getCisternWater() { return cisternWater; }

}