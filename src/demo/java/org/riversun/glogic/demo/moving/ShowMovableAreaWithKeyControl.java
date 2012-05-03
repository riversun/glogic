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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.riversun.glogic.demo.support.EasyCanvas;
import org.riversun.glogic.demo.support.EasyCanvas.ManagedGraphics2D;
import org.riversun.glogic.demo.support.EasyCanvas.Paint;
import org.riversun.glogic.demo.support.EasyCanvas.Style;
import org.riversun.glogic.slg.moving.MovableAreaHelper;

/**
 * Simple Demo for MovableAreaCalc
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 *
 */
public class ShowMovableAreaWithKeyControl {

	private static final int GRID_UNIT_PIXELS = 32;

	private MovableAreaHelper mMah = new MovableAreaHelper();
	private EasyCanvas mCanvas;

	private int mCharacterX;
	private int mCharacterY;
	private int mCharacterMovingPower;

	private int[][] mMap;

	public static void main(String[] args) {

		// position of main character that has moving power
		int characterX = 4;
		int characterY = 4;

		// moving power
		int characterMovingPower = 3;

		// game map
		int[][] map = new int[][] {
				// MAP DATA
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

		new ShowMovableAreaWithKeyControl(characterX, characterY, characterMovingPower, map).startDemo();
	}

	public ShowMovableAreaWithKeyControl(int characterX, int characterY, int characterMovingPower, int[][] map) {
		this.mMap = map;
		this.mCharacterX = characterX;
		this.mCharacterY = characterY;
		this.mCharacterMovingPower = characterMovingPower;
	}

	private final KeyListener mKeyListener = new KeyListener() {

		public void keyTyped(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {

			final int key = e.getKeyCode();

			int px = 0;
			int py = 0;

			// move character to check movable area
			if (key == KeyEvent.VK_LEFT) {
				px = -1;
			}

			if (key == KeyEvent.VK_RIGHT) {
				px = +1;
			}

			if (key == KeyEvent.VK_UP) {
				py = -1;
			}

			if (key == KeyEvent.VK_DOWN) {
				py = +1;
			}

			mCharacterX += px;
			mCharacterY += py;

			if (0 > (mCharacterX) || mMah.getWidth() - 1 < (mCharacterX) || 0 > (mCharacterY) || mMah.getWidth() - 1 < (mCharacterY) //
					// obstacle here?
					|| mMap[mCharacterY][mCharacterX] > mCharacterMovingPower) {
				mCharacterX -= px;
				mCharacterY -= py;
			} else {
				drawUpdate();
			}

		}
	};

	public void startDemo() {

		mMah.setMap(mMap);

		mCanvas = new EasyCanvas(mMah.getWidth() * GRID_UNIT_PIXELS, mMah.getHeight() * GRID_UNIT_PIXELS);

		mCanvas.getFrame().addKeyListener(mKeyListener);

		drawUpdate();
	}

	private void drawUpdate() {

		final int[][] movableArea = mMah.getMovableArea(mCharacterX, mCharacterY, mCharacterMovingPower);

		Paint p = new Paint();
		p.setAntialias(true);

		ManagedGraphics2D g = mCanvas.getManagedGraphics();
		g.clear();

		// draw field
		for (int y = 0; y < mMah.getHeight(); y++) {

			for (int x = 0; x < mMah.getWidth(); x++) {

				int drawX = x * GRID_UNIT_PIXELS;
				int drawY = y * GRID_UNIT_PIXELS;

				// draw grid
				p.setStyle(Style.STROKE);
				p.setColor(Color.BLACK);
				g.drawRect(drawX, drawY, GRID_UNIT_PIXELS, GRID_UNIT_PIXELS, p);

				if (mMap[y][x] > mCharacterMovingPower) {
					// draw Obstacles
					p.setStyle(Style.FILL);
					p.setColor(Color.BLACK);
					g.drawRect(drawX, drawY, GRID_UNIT_PIXELS, GRID_UNIT_PIXELS, p);
				}

			}
		}

		// draw main character
		loop: for (int y = 0; y < mMah.getHeight(); y++) {

			for (int x = 0; x < mMah.getWidth(); x++) {

				if (mCharacterX == x && mCharacterY == y) {

					final int drawX = x * GRID_UNIT_PIXELS;
					final int drawY = y * GRID_UNIT_PIXELS;
					final int radius = GRID_UNIT_PIXELS / 3;

					p.setStyle(Style.FILL);
					p.setColor(Color.BLUE);

					g.drawCircle(drawX + GRID_UNIT_PIXELS/2, drawY + GRID_UNIT_PIXELS/2, radius, p);

					break loop;
				}
			}
		}

		// draw movable area
		for (int y = 0; y < mMah.getHeight(); y++) {

			for (int x = 0; x < mMah.getWidth(); x++) {

				if (movableArea[y][x] > 0) {

					int drawX = x * GRID_UNIT_PIXELS;
					int drawY = y * GRID_UNIT_PIXELS;

					p.setStyle(Style.FILL);
					final Color movableAreaColor = new Color(Long.decode("#220000FF").intValue(), true);
					p.setColor(movableAreaColor);

					g.drawRect(drawX, drawY, GRID_UNIT_PIXELS, GRID_UNIT_PIXELS, p);
				}
			}
		}
	}
}
