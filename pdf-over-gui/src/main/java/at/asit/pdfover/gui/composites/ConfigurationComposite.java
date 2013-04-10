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
package at.asit.pdfover.gui.composites;

// Imports
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.asit.pdfover.gui.workflow.ConfigManipulator;
import at.asit.pdfover.gui.workflow.states.State;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

/**
 * 
 */
public class ConfigurationComposite extends StateComposite {
	
	/**
	 * 
	 */
	private final class ConfigurationModeSelectionListener implements
			SelectionListener {
		/**
		 * 
		 */
		public ConfigurationModeSelectionListener() {
			// Nothing to do
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (ConfigurationComposite.this.configComposite instanceof SimpleConfigurationComposite) {
				// switch to advanced
				ConfigurationComposite.this.configComposite.dispose();
				ConfigurationComposite.this.configComposite = new AdvancedConfigurationComposite(
						ConfigurationComposite.this.containerComposite, 
						ConfigurationComposite.this.style,
						ConfigurationComposite.this.state,
						ConfigurationComposite.this.configurationContainer);
				ConfigurationComposite.this.btnAdvanced.setText("Simple");
			} else {
				// switch to simple
				ConfigurationComposite.this.configComposite.dispose();
				ConfigurationComposite.this.configComposite = new SimpleConfigurationComposite(
						ConfigurationComposite.this.containerComposite, 
						ConfigurationComposite.this.style,
						ConfigurationComposite.this.state,
						ConfigurationComposite.this.configurationContainer);
				ConfigurationComposite.this.btnAdvanced.setText("Advanced");
			}
			
			ConfigurationComposite.this.compositeStack.topControl = ConfigurationComposite.this.configComposite;
			ConfigurationComposite.this.doLayout();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// Nothing to do
		}
	}

	/**
	 * SLF4J Logger instance
	 **/
	private static final Logger log = LoggerFactory
			.getLogger(ConfigurationComposite.class);

	ConfigManipulator configManipulator = null;

	BaseConfigurationComposite configComposite;

	ConfigurationContainer configurationContainer = new ConfigurationContainerImpl();
	
	StackLayout compositeStack = new StackLayout();

	int style;

	Composite containerComposite;

	boolean userDone = false;

	Button btnAdvanced;

	/**
	 * Sets the configuration manipulator
	 * 
	 * @param manipulator
	 */
	public void setConfigManipulator(ConfigManipulator manipulator) {
		this.configManipulator = manipulator;
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param state
	 */
	public ConfigurationComposite(Composite parent, int style, State state) {
		super(parent, SWT.FILL | style, state);
		this.style = SWT.FILL | style;

		this.setLayout(new FormLayout());

		this.containerComposite = new Composite(this, SWT.FILL | SWT.RESIZE);

		this.configComposite = new SimpleConfigurationComposite(
				this.containerComposite, SWT.FILL | style, state, this.configurationContainer);

		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 5);
		fd_composite.bottom = new FormAttachment(90, -5);
		fd_composite.left = new FormAttachment(0, 5);
		fd_composite.right = new FormAttachment(100, -5);
		this.containerComposite.setLayoutData(fd_composite);
		this.containerComposite.setLayout(this.compositeStack);
		this.compositeStack.topControl = this.configComposite;

		this.doLayout();

		Button btnSpeichern = new Button(this, SWT.NONE);
		FormData fd_btnSpeichern = new FormData();
		fd_btnSpeichern.left = new FormAttachment(0, 5);
		fd_btnSpeichern.bottom = new FormAttachment(100, -5);
		btnSpeichern.setLayoutData(fd_btnSpeichern);
		btnSpeichern.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnSpeichern.setText("Speichern");

		Button btnAbbrechen = new Button(this, SWT.NONE);
		FormData fd_btnAbrechen = new FormData();
		fd_btnAbrechen.left = new FormAttachment(btnSpeichern, 10);
		fd_btnAbrechen.bottom = new FormAttachment(100, -5);
		btnAbbrechen.setLayoutData(fd_btnAbrechen);
		btnAbbrechen.setText("Abbrechen");
		btnAbbrechen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ConfigurationComposite.this.userDone = true;
				ConfigurationComposite.this.state.updateStateMachine();
			}
		});

		this.btnAdvanced = new Button(this, SWT.NONE);
		FormData fd_btnAdvanced = new FormData();
		fd_btnAdvanced.right = new FormAttachment(100, -5);
		fd_btnAdvanced.bottom = new FormAttachment(100, -5);
		this.btnAdvanced.setLayoutData(fd_btnAdvanced);
		this.btnAdvanced.setText("Advanced");
		this.btnAdvanced
				.addSelectionListener(new ConfigurationModeSelectionListener());

	}

	private void storeConfiguration() {
		// TODO: Collect info from UI and set in ConfigManipulator

		// TODO: call save configuration in ConfigManipulator
	}

	/**
	 * Checks if the user has finished working with the configuration composite
	 * 
	 * @return
	 */
	public boolean isUserDone() {
		return this.userDone;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.asit.pdfover.gui.composites.StateComposite#doLayout()
	 */
	@Override
	public void doLayout() {
		Control ctrl = this.compositeStack.topControl;
		this.containerComposite.layout(true, true);
		this.getShell().layout(true, true);
		// Note: SWT only layouts children! No grandchildren!
		if (ctrl instanceof StateComposite) {
			((StateComposite) ctrl).doLayout();
		}
	}
}