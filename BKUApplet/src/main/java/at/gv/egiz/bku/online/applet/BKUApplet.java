/*
 * Copyright 2008 Federal Chancellery Austria and
 * Graz University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.gv.egiz.bku.online.applet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JApplet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.gv.egiz.bku.gui.BKUGUIFacade;
import at.gv.egiz.bku.gui.BKUGUIFactory;

/**
 * Note: all swing code is executed by the event dispatch thread (see
 * BKUGUIFacade)
 */
public class BKUApplet extends JApplet implements AppletParameterProvider {

  private static Log log = LogFactory.getLog(BKUApplet.class);
  
  /**
   * Applet parameter keys
   */
  public static final String GUI_STYLE = "GuiStyle";
  public final static String LOCALE_PARAM_KEY = "Locale";
  public final static String LOGO_URL_KEY = "LogoURL";
  public final static String WSDL_URL = "WSDL_URL";
  public static final String HASHDATA_DISPLAY = "HashDataDisplay";
  public final static String HASHDATA_URL = "HashDataURL";
  public final static String HELP_URL = "HelpURL";
  public final static String SESSION_ID = "SessionID";
  public static final String BACKGROUND_PARAM = "Background";
  public static final String REDIRECT_URL = "RedirectURL";
  public static final String REDIRECT_TARGET = "RedirectTarget";
  public static final String HASHDATA_DISPLAY_INTERNAL = "internal";
  
  /**
   * STAL WSDL namespace and service name
   */
  public static final String STAL_WSDL_NS = "http://www.egiz.gv.at/wsdl/stal";
  public static final String STAL_SERVICE = "STALService";
  
  /**
   * Dummy session id, used if no sessionId parameter is provided
   */
  protected static final String TEST_SESSION_ID = "TestSession";
  
  /**
   * STAL
   */
  protected AppletBKUWorker worker;
  protected Thread workerThread;

  /**
   * Factory method to create and wire HelpListener, GUI and BKUWorker.
   */
  @Override
  public void init() {
    log.info("Welcome to MOCCA");
    log.debug("Called init()");
    
    HttpsURLConnection.setDefaultSSLSocketFactory(InternalSSLSocketFactory.getInstance());
    
    String locale = getAppletParameter(LOCALE_PARAM_KEY);
    String guiStyle = getAppletParameter(GUI_STYLE);
    URL backgroundImgURL = null;
    URL helpURL = null;
    try {
      helpURL = getURLParameter(HELP_URL); //, getAppletParameter(SESSION_ID));
    } catch (MalformedURLException ex) {
      log.warn("failed to load help URL, disabling help: " + ex.getMessage());
    }
    try {
      backgroundImgURL = getURLParameter(BACKGROUND_PARAM);
    } catch (MalformedURLException ex) {
      log.info("failed to load applet background image, using default: " + ex.getMessage());
    }
    
    if (locale != null) {
      this.setLocale(new Locale(locale));
    }
    log.debug("setting locale to " + getLocale());

    BKUGUIFacade gui = BKUGUIFactory.createGUI(guiStyle);
    AppletHelpListener helpListener = new AppletHelpListener(helpURL, getLocale()); //getAppletContext(),
    gui.init(getContentPane(), getLocale(), backgroundImgURL, helpListener);

    worker = new AppletBKUWorker(gui, getAppletContext(), this);
  }

  @Override
  public void start() {
    log.debug("Called start()");
    workerThread = new Thread(worker);
    workerThread.start();
  }

  @Override
  public void stop() {
    log.debug("Called stop()");
    if ((workerThread != null) && (workerThread.isAlive())) {
      workerThread.interrupt();
    }
  }

  @Override
  public void destroy() {
    log.debug("Called destroy()");
  }

  @Override
  public String getAppletParameter(String paramKey) {
    String param = getParameter(paramKey);
    log.info("applet parameter: " + paramKey + ": " + param);
    return param;
  }

  @Override
  public URL getURLParameter(String paramKey, String sessionId) throws MalformedURLException {
    String urlParam = getParameter(paramKey);
    if (urlParam != null) {
      URL codebase = getCodeBase();
      try {
        URL url;
        if (codebase.getProtocol().equalsIgnoreCase("file")) {
          // for debugging in appletrunner
          url = new URL(urlParam);
        } else {
          if (sessionId != null) {
            urlParam = urlParam + ";jsessionid=" + sessionId;
          }
          url = new URL(codebase, urlParam);
        }
        log.info("applet parameter " + url);
        return url;
      } catch (MalformedURLException ex) {
        log.error("applet paremeter " + urlParam + " is not a valid URL: " + ex.getMessage());
        throw ex;
      }      
    } else {
      log.error("applet paremeter " + urlParam + " not set");
      throw new MalformedURLException(urlParam + " not set");
    }
  }
  
  @Override
  public URL getURLParameter(String paramKey) throws MalformedURLException {
    return getURLParameter(paramKey, null);
  }
}
