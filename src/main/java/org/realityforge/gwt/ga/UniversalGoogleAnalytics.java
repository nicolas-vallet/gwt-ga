package org.realityforge.gwt.ga;

public interface UniversalGoogleAnalytics {
	
	public void init(String userAccount);
	
	public void trackPageview();
	
	public void trackPageview(String pageName);
	
	public void trackEvent(String category, String action);
	
	public void trackEvent(String category, String action, String label);
	
	public void trackEvent(String category, String action, String label, int value);

}
