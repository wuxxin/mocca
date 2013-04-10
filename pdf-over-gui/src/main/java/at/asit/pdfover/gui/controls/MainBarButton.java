/*
 * Copyright 2012 by A-SIT, Secure Information Technology Center Austria
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package at.asit.pdfover.gui.controls;

// Imports
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.asit.pdfover.gui.composites.StateComposite;

/**
 * Main Bar Button implementation
 */
public abstract class MainBarButton extends Canvas {
	
	/**
	 * If borders are drawn with a gradient effect this sets the size
	 */
	public static final int GradientFactor = 5;
	
	/**
	 * Number of pixel of the altitude of the triangle representing the arrow within the button shapes
	 * 
	 * This should be a multiple of 2!
	 */
	public static final int SplitFactor = 10;
	
	/**
	 * @param parent
	 * @param style
	 */
	public MainBarButton(Composite parent, int style) {
		super(parent, style);
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				MainBarButton.this.paintControl(e);
			}
		});

		final Cursor hand = new Cursor(this.getDisplay(), SWT.CURSOR_HAND);

		this.addListener(SWT.Resize, new Listener() {

			@Override
			public void handleEvent(Event event) {
				MainBarButton.this.setRegion(MainBarButton.this
						.getCustomRegion());
				MainBarButton.this.redraw();
			}
		});

		this.setCursor(hand);


		this.inactiveBackground = new Color(getDisplay(),0xD4, 0xE7, 0xF1);
		this.activeBackground1 = new Color(getDisplay(),0x6B, 0xA5, 0xD9);
		this.activeBackground = new Color(getDisplay(),0xB4, 0xCD, 0xEC);
		//this.textColor = this.getForeground();
		this.textColor = new Color(getDisplay(), 0x00, 0x00, 0x00);
		//this.borderColor = new Color(getDisplay(), 0xf9, 0xf9, 0xf9);
		this.borderColor = this.getBackground();
		this.inactiveText = new Color(getDisplay(), 0x40, 0x40, 0x40);
		this.textsize = StateComposite.TEXT_SIZE_BUTTON;

	}

	private Color inactiveBackground = null;
	private Color activeBackground1 = null;

	/**
	 * @param inactiveBackground
	 *            the inactiveBackground to set
	 */
	public void setInactiveBackground(Color inactiveBackground) {
		this.inactiveBackground = inactiveBackground;
	}

	/**
	 * @param activeBackground
	 *            the activeBackground to set
	 */
	public void setActiveBackground(Color activeBackground) {
		this.activeBackground = activeBackground;
	}

	/**
	 * the text size
	 */
	protected int textsize = StateComposite.TEXT_SIZE_BUTTON;

	/**
	 * @return the textsize
	 */
	public int getTextsize() {
		return this.textsize;
	}

	/**
	 * @param textsize
	 *            the textsize to set
	 */
	public void setTextsize(int textsize) {
		this.textsize = textsize;
	}

	/**
	 * the used text color
	 */
	protected Color textColor = null;

	/**
	 * @param textColor
	 *            the textColor to set
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	/**
	 * @return the borderColor
	 */
	public Color getBorderColor() {
		return this.borderColor;
	}

	/**
	 * @param borderColor
	 *            the borderColor to set
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	

	private Color borderColor = null;

	private Color activeBackground = null;
	
	private Color inactiveText = null;

	private String text = ""; //$NON-NLS-1$

	private boolean active = true;

	private Image image = null;

	/**
	 * Gets the image
	 * 
	 * @return the image
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * Sets the Image
	 * 
	 * @param image
	 *            the imgage to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Sets if this button is active
	 * 
	 * @param active
	 *            the active state
	 */
	public void setActive(boolean active) {

		this.active = active;
		if (this.active) {
			this.setBackground(this.activeBackground);
		} else {
			this.setBackground(this.inactiveBackground);
		}
	}

	/**
	 * Gets if this button is active
	 * 
	 * @return the active state
	 */
	public boolean getActive() {
		return this.active;
	}

	/**
	 * Gets the button text
	 * 
	 * @return the text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text for the button
	 * 
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * SLF4J Logger instance
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(MainBarButton.class);

	/**
	 * Paint 3D style borders
	 * 
	 * @param e
	 */
	protected void paintBackground(PaintEvent e) {
		Point size = this.getSize();
		int height = size.y - 4;

		int width = size.x;
		
		e.gc.setForeground(this.activeBackground1);
		e.gc.setBackground(this.activeBackground);
		
		e.gc.fillGradientRectangle(0, height, width, -1 * height, true);
		
		//e.gc.setBackground(activeBackground);
		
		// LEFT
		// e.gc.fillGradientRectangle(0, 0, factor, height, false);

		// RIGTH
		// e.gc.fillGradientRectangle(width, 0, -1 * (width / factor), height,
		// false);

	}

	/**
	 * Main painting method
	 * 
	 * @param e
	 */
	void paintControl(PaintEvent e) {
		Color forecurrent = e.gc.getForeground();
		Color backcurrent = e.gc.getBackground();
		
		e.gc.setForeground(getBorderColor());
		if(this.getActive()) {
			this.paintBackground(e);
		}

		e.gc.setForeground(getBorderColor());
		
		this.paintButton(e);

		e.gc.setForeground(forecurrent);
		e.gc.setBackground(backcurrent);

		this.paintText(e);
	}

	/**
	 * paint the inner button
	 * 
	 * @param e
	 */
	protected void paintButton(PaintEvent e) {
		// could be overwritten by subclasses
	}

	/**
	 * Paint the text or image on the button
	 * 
	 * @param e
	 */
	protected void paintText(PaintEvent e) {
		Point size = this.getSize();
		int height = size.y;

		int width = size.x;

		// e.gc.fillGradientRectangle(0, 1, width, height / 4, true);

		if (this.image == null) {
			int textlen = 0;

			if (this.getText() != null) {
				textlen = this.getText().length();
			}

			Color current = e.gc.getForeground();

			if(this.getActive() && this.isEnabled()) {
				e.gc.setForeground(this.textColor);
			} else {
				e.gc.setForeground(this.inactiveText);
			}

			String font_name = e.gc.getFont().getFontData()[0].getName();

			Font font = new Font(this.getDisplay(), font_name,
					this.getTextsize(),
					e.gc.getFont().getFontData()[0].getStyle());

			e.gc.setFont(font);

			int texty = (height - e.gc.getFontMetrics().getHeight()) / 2;

			int textx = (width - e.gc.getFontMetrics().getAverageCharWidth()
					* textlen) / 2;

			textx = this.changeTextPosition(textx);
			
			e.gc.drawText(this.getText(), textx, texty, true);

			font.dispose();

			e.gc.setForeground(current);
		} else {
			
			//log.debug("Width: " + width + " Height: " + height);
			
			int w = 0;
			Image tmp = null;
			if(this.image.getImageData().width < width) {
				tmp = new Image(getDisplay(), this.image.getImageData());
				w = (width - this.image.getImageData().width) / 2;
			} else if(this.image.getImageData().width > width) {
				tmp = new Image(getDisplay(), this.image.getImageData().scaledTo(width, width));
			} else {
				tmp = new Image(getDisplay(), this.image.getImageData());
			}
			
			e.gc.drawImage(tmp, w, w);
		}

	}

	/**
	 * change the text position
	 * 
	 * @param positionX
	 *            the position
	 * @return the new position
	 */
	protected int changeTextPosition(int positionX) {
		return positionX;
	}

	/**
	 * Gets the region of the button
	 * 
	 * @return the button region
	 */
	protected abstract Region getCustomRegion();
}