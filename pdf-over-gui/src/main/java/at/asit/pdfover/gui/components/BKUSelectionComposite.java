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
package at.asit.pdfover.gui.components;

// Imports
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.asit.pdfover.gui.workflow.StateMachine;
import at.asit.pdfover.gui.workflow.states.BKUSelectionState;
import at.asit.pdfover.gui.workflow.states.BKUSelectionState.BKUs;

/**
 * 
 */
public class BKUSelectionComposite extends Composite implements StateComposite {
	
	
	/**
	 * Listener for local bku selection 
	 */
	private final class LocalSelectionListener implements SelectionListener {
		
		/**
		 * Default constructor 
		 */
		public LocalSelectionListener() {
			// Nothing here
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			log.debug("Setting BKU to LOCAL");
			setSelected(BKUs.LOCAL);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// Nothing here
		}
	}
	
	/**
	 * Listener for mobile bku selection 
	 */
	private final class MobileSelectionListener implements SelectionListener {
		
		/**
		 * Default constructor
		 */
		public MobileSelectionListener() {
			// Nothing here
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			log.debug("Setting BKU to MOBILE");
			setSelected(BKUs.MOBILE);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// Nothing here
		}
	}

	/**
	 * SLF4J Logger instance
	 **/
	private static final Logger log = LoggerFactory
			.getLogger(BKUSelectionComposite.class);

	private BKUSelectionState state;
	
	private BKUs selected = BKUs.NONE;
	
	/**
	 * Gets selected BKU type
	 * @return BKUS enum
	 */
	public BKUs getSelected() {
		return this.selected;
	}

	/**
	 * Sets selected BKU and updates workflow
	 * @param selected
	 */
	public void setSelected(final BKUs selected) {
		this.selected = selected;
		this.state.updateStateMachine();
	}

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BKUSelectionComposite(Composite parent, int style, BKUSelectionState state) {
		super(parent, style);

		this.state = state;

		this.setLayout(new FormLayout());
		
		
		Button btn_mobile = new Button(this, SWT.NATIVE | SWT.RESIZE);
		btn_mobile.setText("MOBILE");
		//Point mobile_size = btn_mobile.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		FormData fd_btn_mobile = new FormData();
		fd_btn_mobile.left = new FormAttachment(40, 0);
		fd_btn_mobile.right = new FormAttachment(50, 0);
		fd_btn_mobile.top = new FormAttachment(45, 0);
		fd_btn_mobile.bottom = new FormAttachment(55, 0);
		btn_mobile.setLayoutData(fd_btn_mobile);
		btn_mobile.addSelectionListener(new MobileSelectionListener());

		Button btn_card = new Button(this, SWT.NATIVE | SWT.RESIZE);
		btn_card.setText("CARD");
		//Point card_size = btn_card.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		FormData fd_btn_card = new FormData();
		fd_btn_card.left = new FormAttachment(50, 0);
		fd_btn_card.right = new FormAttachment(60, 0);
		fd_btn_card.top = new FormAttachment(45, 0);
		fd_btn_card.bottom = new FormAttachment(55, 0);
		btn_card.setLayoutData(fd_btn_card);
		btn_card.addSelectionListener(new LocalSelectionListener());
		
		this.pack();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/* (non-Javadoc)
	 * @see at.asit.pdfover.gui.components.StateComposite#doLayout()
	 */
	@Override
	public void doLayout() {
		this.layout(true, true);
	}

}
