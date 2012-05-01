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
package org.riversun.glogic.slg;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Canvas that is easy to draw simple graphics placed on Simple Window
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 *
 */
public class EasyCanvas {

	public static void main(String[] args) {

		// create canvas on the window
		EasyCanvas ec = new EasyCanvas(640, 480);

		// get managed graphics 2d (also you can get graphics2D by
		// getGraphics())
		ManagedGraphics2D mg = ec.getManagedGraphics();

		// android like Paint class
		Paint p = new Paint();
		p.setAntialias(true);
		p.setStyle(Style.STROKE);

		// drawRect(x,y,width,height,paint)
		mg.drawRect(0, 0, 100, 100, p);

		// drawCircle(centerX,centerY,radius,paint)
		mg.drawCircle(320, 240, 30, p);
		mg.drawCircle(320, 240, 50, p);

		// Allow you to specify text size by Paint
		p.setTextSize(20);
		// Allow you to specify color (of text and other draw methods) by Paint
		p.setColor(Color.red);

		// drawText(x,y,text,Anchor of text,paint)
		mg.drawText(320, 240, "Test", TextAnchor.CENTER_CENTER, p);

		// Update painting
		ec.invalidate();
	}

	private final BufferedImage mBufferedImage;
	private final Graphics2D mGraphics2D;
	private final InternalCanvas mCanvas;
	private final ManagedGraphics2D mManagedGraphics2D;

	public EasyCanvas(int width, int height) {

		this.mBufferedImage = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_ARGB);
		this.mGraphics2D = mBufferedImage.createGraphics();
		this.mManagedGraphics2D = new ManagedGraphics2D(EasyCanvas.this);
		this.mCanvas = new InternalCanvas();

		final JFrame frame = new JFrame();
		frame.add(mCanvas);
		frame.addWindowListener(new InternalWindowAdapter());

		// to fix content area size
		frame.getContentPane().setPreferredSize(new Dimension(width, height));
		frame.pack();

		frame.setResizable(false);
		frame.setVisible(true);

		invalidate();
	}

	public ManagedGraphics2D getManagedGraphics() {
		return mManagedGraphics2D;
	}

	public Graphics2D getGraphics() {
		return mGraphics2D;
	}

	public void invalidate() {
		mCanvas.repaint();
	}

	public enum Style {
		FILL, STROKE
	}

	public static class ManagedGraphics2D {

		private final EasyCanvas mEasyCanvas;
		private final Graphics2D mG2d;

		public ManagedGraphics2D(EasyCanvas ec) {
			mEasyCanvas = ec;
			mG2d = ec.mGraphics2D;
		}

		private Paint mDefaultPaint = new Paint();

		private Paint setupPaint(Paint paint) {

			final Paint _paint = getSafePaint(paint);

			mG2d.setFont(_paint.getFont());
			mG2d.setColor(_paint.getColor());

			if (_paint.isAntiAlias()) {
				mG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			} else {
				mG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			}
			return _paint;
		}

		private Paint getSafePaint(Paint paint) {

			final Paint _paint;

			if (paint == null) {
				_paint = mDefaultPaint;
			} else {
				_paint = paint;
			}
			return _paint;

		}

		/**
		 * Draw rectangle
		 * 
		 * @param left
		 * @param top
		 * @param width
		 * @param height
		 * @param paint
		 */
		public void drawRect(int left, int top, int width, int height, Paint paint) {

			final Paint _paint = setupPaint(paint);
			final Style style = _paint.getStyle();

			switch (style) {

			case FILL:
				mG2d.fillRect(left, top, width, height);
			case STROKE:
				mG2d.drawRect(left, top, width, height);
			}

			mEasyCanvas.invalidate();

		}

		/**
		 * Draw circle
		 * 
		 * @param cx
		 * @param cy
		 * @param radius
		 * @param paint
		 */
		public void drawCircle(int cx, int cy, int radius, Paint paint) {

			final Paint _paint = setupPaint(paint);
			final Style style = _paint.getStyle();

			final int left = cx - radius;
			final int top = cy - radius;
			final int width = radius * 2;
			final int height = radius * 2;

			switch (style) {
			case FILL:
				mG2d.fillOval(left, top, width, height);
			case STROKE:
				mG2d.drawOval(left, top, width, height);
			}

			mEasyCanvas.invalidate();
		}

		/**
		 * Draw line
		 * 
		 * @param cx
		 * @param cy
		 * @param radius
		 * @param paint
		 */
		public void drawLine(int x1, int y1, int x2, int y2, Paint paint) {

			final Paint _paint = setupPaint(paint);
			final Style style = _paint.getStyle();

			switch (style) {
			case FILL:
			case STROKE:
				mG2d.drawLine(x1, y1, x2, y2);
			}

			mEasyCanvas.invalidate();
		}

		/**
		 * Draw text
		 * 
		 * @param x
		 * @param y
		 * @param text
		 * @param anchor
		 * @param paint
		 */
		public void drawText(int x, int y, String text, TextAnchor anchor, Paint paint) {

			setupPaint(paint);

			final FontRenderContext frContext = mG2d.getFontRenderContext();
			final GlyphVector gvec = mG2d.getFont().createGlyphVector(frContext, text);
			final Rectangle rect = gvec.getPixelBounds(null, x, y);

			int drawX = x;
			int drawY = y;

			int px = 0;
			int py = 0;

			switch (anchor) {
			case LEFT_TOP:
				px = 0;
				py = rect.height;
				break;
			case CENTER_TOP:
				px = -rect.width / 2;
				py = rect.height;
				break;
			case RIGHT_TOP:
				px = -rect.width;
				py = rect.height;
				break;
			case LEFT_CENTER:
				px = 0;
				py = +rect.height / 2;
				break;
			case CENTER_CENTER:
				px = -rect.width / 2;
				py = +rect.height / 2;
				break;
			case RIGHT_CENTER:
				px = -rect.width;
				py = +rect.height / 2;
				break;
			case LEFT_BOTTOM:
				px = 0;
				py = 0;
				break;
			case CENTER_BOTTOM:
				px = -rect.width / 2;
				py = 0;
				break;
			case RIGHT_BOTTOM:
				px = -rect.width;
				py = 0;
				break;

			}

			drawX += px;
			drawY += py;

			mG2d.drawString(text, drawX, drawY);
		}
	}

	public enum TextAnchor {
		LEFT_TOP, CENTER_TOP, RIGHT_TOP, LEFT_CENTER, CENTER_CENTER, RIGHT_CENTER, LEFT_BOTTOM, CENTER_BOTTOM, RIGHT_BOTTOM,
	}

	// inner classes
	public static class Paint {

		private Style mStyle = Style.FILL;
		private Color mColor = Color.BLACK;

		private boolean mAntiAlias = false;
		private int mTextSize = 16;

		private String mFontName = "Arial";
		private Font mFont = new Font(mFontName, Font.PLAIN, mTextSize);

		public void setTextSize(int textSize) {
			mFont = new Font(mFontName, Font.PLAIN, textSize);
			mTextSize = textSize;
		}

		public int getTextSize() {
			return mTextSize;
		}

		Font getFont() {
			return mFont;
		}

		public void setAntialias(boolean enabled) {
			mAntiAlias = enabled;
		}

		public boolean isAntiAlias() {
			return mAntiAlias;
		}

		public Style getStyle() {
			return mStyle;
		}

		public void setStyle(Style style) {
			this.mStyle = style;
		}

		public Color getColor() {
			return mColor;
		}

		public void setColor(Color color) {
			this.mColor = color;
		}

	}

	@SuppressWarnings("serial")
	private class InternalCanvas extends Canvas {

		public void paint(Graphics g) {
			g.drawImage(mBufferedImage, 0, 0, null);
		}
	}

	private class InternalWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}
