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
package org.riversun.glogic.demo.moving;

import org.riversun.glogic.slg.moving.MovableAreaCalc;

/**
 * Simple Demo for MovableAreaCalc
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 *
 */
public class ShowMovableArea {

	public static void main(String[] args) {

		MovableAreaCalc ma = new MovableAreaCalc();

		int characterX = 6;
		int characterY = 6;
		int characterMovingPower = 4;

		int[][] map = new int[][] {
				// 0,1, 2, 3, 4, 5, 6, 7, 8, 9
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 0
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 1
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 2
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 3
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 4
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 5
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 6
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 7
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 8
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 9
		};

		ma.setMap(map);

		int[][] movableArea = ma.getMovableArea(characterX, characterY, characterMovingPower);

		for (int y = 0; y < ma.getHeight(); y++) {
			System.out.println();
			for (int x = 0; x < ma.getWidth(); x++) {
				if (characterX == x && characterY == y) {
					System.out.print("    * ");
				} else {
					System.out.print(String.format("%1$5d ", movableArea[y][x]));
				}
			}
		}

	}
}
