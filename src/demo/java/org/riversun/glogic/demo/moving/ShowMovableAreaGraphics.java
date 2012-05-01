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

import java.awt.Color;

import org.riversun.glogic.demo.support.EasyCanvas;
import org.riversun.glogic.demo.support.EasyCanvas.ManagedGraphics2D;
import org.riversun.glogic.demo.support.EasyCanvas.Paint;
import org.riversun.glogic.demo.support.EasyCanvas.Style;
import org.riversun.glogic.slg.moving.MovableAreaCalc;

/**
 * Simple Demo for MovableAreaCalc
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 *
 */
public class ShowMovableAreaGraphics {

	public static void main(String[] args) {

		MovableAreaCalc ma = new MovableAreaCalc();

		int characterX = 4;
		int characterY = 4;
		int characterMovingPower = 3;

		int[][] map = new int[][] {
				// 0,1, 2, 3, 4, 5, 6, 7, 8, 9
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 0
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 1
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 2
				{ 1, 1, 1, 1, 1, 9, 1, 1, 1, 1 },// 3
				{ 1, 1, 1, 1, 1, 9, 1, 1, 1, 1 },// 4
				{ 1, 1, 1, 1, 1, 9, 1, 1, 1, 1 },// 5
				{ 1, 1, 1, 1, 9, 9, 1, 1, 1, 1 },// 6
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 7
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 8
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },// 9
		};

		ma.setMap(map);

		int[][] movableArea = ma.getMovableArea(characterX, characterY, characterMovingPower);

		int charSize = 32;

		EasyCanvas ec = new EasyCanvas(ma.getWidth() * charSize, ma.getHeight() * charSize);
		Paint p = new Paint();
		p.setAntialias(true);
		ManagedGraphics2D mg = ec.getManagedGraphics();

		// draw field
		for (int y = 0; y < ma.getHeight(); y++) {

			for (int x = 0; x < ma.getWidth(); x++) {

				int dx = x * charSize;
				int dy = y * charSize;

				// draw grid
				p.setStyle(Style.STROKE);
				p.setColor(Color.BLACK);
				mg.drawRect(dx, dy, charSize, charSize, p);

				if (map[y][x] > characterMovingPower) {
					// draw Obstacles
					p.setStyle(Style.FILL);
					p.setColor(Color.BLACK);
					mg.drawRect(dx, dy, charSize, charSize, p);

				}

			}
		}

		// draw main character
		loop: for (int y = 0; y < ma.getHeight(); y++) {

			for (int x = 0; x < ma.getWidth(); x++) {

				if (characterX == x && characterY == y) {

					int dx = x * charSize;
					int dy = y * charSize;
					int radius = charSize / 2;

					p.setStyle(Style.FILL);
					p.setColor(Color.BLUE);

					mg.drawCircle(dx - radius, dy - radius, radius, p);
					break loop;
				}
			}
		}

		// draw movable area
		for (int y = 0; y < ma.getHeight(); y++) {

			for (int x = 0; x < ma.getWidth(); x++) {

				if (movableArea[y][x] > 0) {

					int dx = x * charSize;
					int dy = y * charSize;

					p.setStyle(Style.FILL);

					Color decode = new Color(Long.decode("#220000FF").intValue(), true);
					p.setColor(decode);

					mg.drawRect(dx, dy, charSize, charSize, p);
				}
			}
		}

	}
}
