/**
 * Copyright 2006-2016 Tom Misawa(riversun.org@gmail.com)
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.riversun.glogic.slg.moving;

/**
 * To calculate the movable range of the character with a moving power on the
 * 2-dimension array list based Map in the kind of Simulation Game,Strategic
 * Simulation Game
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 */
public class MovableAreaCalc {

	public static final int MOVABLE = 1;
	public static final int CANNOT_MOVABLE = 0;

	private int[][] mOriginalMap;
	private int[][] mWorkingMap;

	private int mWidth;
	private int mHeight;

	/**
	 * Set original 2-dimension int array map data<br>
	 * <br>
	 * Map data contains hurdle-value which consumes moving power. hurdle-value
	 * must be plus integer.<br>
	 * If hurdle-value is 1,the position that set hurdle-value consume 1 moving
	 * power.
	 * 
	 * @param mapData
	 */
	public void setMap(int[][] mapData) {
		mOriginalMap = mapData;
		mHeight = mOriginalMap.length;
		mWidth = mOriginalMap[0].length;
		mWorkingMap = new int[mHeight][mWidth];
	}

	public int[][] getMovableArea(int targetX, int targetY, int movingPower) {

		for (int y = 0; y < mHeight; y++) {
			for (int x = 0; x < mWidth; x++) {
				setWorkingMapVal(x, y, -1);
			}
		}

		searchDirections(targetX, targetY, movingPower);

		for (int y = 0; y < mHeight; y++) {

			for (int x = 0; x < mWidth; x++) {

				if (getWorkingMapVal(x, y) >= 0) {
					setWorkingMapVal(x, y, MOVABLE);
				} else {
					setWorkingMapVal(x, y, CANNOT_MOVABLE);
				}

			}
		}
		return mWorkingMap;

	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	/**
	 * Search Up/Down/Left/Right from target position
	 * 
	 * @param targetX
	 * @param targetY
	 * @param movingPower
	 */
	private void searchDirections(int targetX, int targetY, int movingPower) {

		// check left
		search(targetX - 1, targetY, movingPower);

		// check right
		search(targetX + 1, targetY, movingPower);

		// check upper
		search(targetX, targetY - 1, movingPower);

		// check bottom
		search(targetX, targetY + 1, movingPower);

	}

	/**
	 * Search position recursively
	 * 
	 * @param searchX
	 * @param searchY
	 * @param currentMovingPower
	 */
	private void search(int searchX, int searchY, int currentMovingPower) {

		if (searchX < 0 || mWidth - 1 < searchX || searchY < 0 || mHeight - 1 < searchY) {
			return;
		}

		final int movingPowerRest = currentMovingPower - getHurdleOf(searchX, searchY);

		if (getWorkingMapVal(searchX, searchY) >= movingPowerRest) {
			// When the point that is already checked,
			// skip duplicated scan
			return;
		}

		setWorkingMapVal(searchX, searchY, movingPowerRest);

		if (movingPowerRest > 0) {
			// If power rest
			searchDirections(searchX, searchY, movingPowerRest);
		} else {
			// do nothing
		}
	}

	private int getHurdleOf(int x, int y) {
		final int hurdle = mOriginalMap[y][x];
		return hurdle;
	}

	private void setWorkingMapVal(int x, int y, int value) {
		mWorkingMap[y][x] = value;
	}

	private int getWorkingMapVal(int x, int y) {
		return mWorkingMap[y][x];
	}
}
