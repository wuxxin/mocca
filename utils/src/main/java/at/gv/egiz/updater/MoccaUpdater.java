package at.gv.egiz.updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JDialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.gv.egiz.slbinding.impl.SignatureLocationType;


public class MoccaUpdater  {
	
	private String MoccaVersionLocal = null;
	private String MoccaVersionOnline = null; 
    private final Logger log = LoggerFactory.getLogger(SignatureLocationType.class);
	
	public MoccaUpdater(String version) {
		setMoccaVersionLocal(version);
	}
	
	private String getMoccaVersionOnline() {
		return MoccaVersionOnline;
	}

	private void setMoccaVersionOnline(String moccaVersionOnline) {
		MoccaVersionOnline = moccaVersionOnline;
	}
	
	private String getMoccaVersionLocal() {
		return MoccaVersionLocal;
	}

	private void setMoccaVersionLocal(String moccaVersionLocal) {
		MoccaVersionLocal = moccaVersionLocal;
	}
	
	
	public void run() {
		
//		if (getMoccaVersionLocal().toLowerCase().equals("unknown")) {
//			// we are finished here
//			return; 
//		}
		
		gatherOnlineMoccaVersion();
		boolean isOnlineNewer = isOnlineVersionNewer();
		if (isOnlineNewer) {
			notifyUserNewerVersionOnline();
		}
	}
	
	
	private void gatherOnlineMoccaVersion() {
		try {
			log.info("Requesting Mocca Online Version");
			URL url = new URL(Constants.PATH_TO_VERSION_FILE);
			log.debug("Going to GET mocca Version from: " + Constants.PATH_TO_VERSION_FILE);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder result = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			setMoccaVersionOnline(result.toString());
			log.info("Online Mocca Version: " + result.toString());
		} catch (IOException e) {
			log.error("Error when gathering Mocca Online Version " + e.getMessage());
		}

	}
	
	private boolean isOnlineVersionNewer() {
		
		VersionComparator comparator = new VersionComparator();
		int result = comparator.compare(getMoccaVersionOnline(), getMoccaVersionLocal());
		return (result>0) ? true : false; 
	}
	
	private void notifyUserNewerVersionOnline() {
		try {
			NewVersionDialog dialog = new NewVersionDialog(getMoccaVersionOnline());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
	
	public static void main(String[] args)  {
		
		MoccaUpdater updater = new MoccaUpdater("1.2.3");
		updater.run();

	}


	
	
	
	

}
