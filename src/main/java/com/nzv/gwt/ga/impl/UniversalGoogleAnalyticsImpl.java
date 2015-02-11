package com.nzv.gwt.ga.impl;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.nzv.gwt.ga.UniversalGoogleAnalytics;

public class UniversalGoogleAnalyticsImpl implements UniversalGoogleAnalytics {

	@Override
	public void init(String userAccount) {
		final Element firstScript = Document.get().getElementsByTagName( "script" ).getItem( 0 );
	    final String source = "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){"
	    		+ "(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),"
	    		+ "m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)"
	    		+ "})(window,document,'script','//www.google-analytics.com/analytics.js','ga'); "
	    		+ "ga('create', '"+userAccount+"', 'auto');"
				+ "ga('send', 'pageview');";
	    
	    final ScriptElement config = Document.get().createScriptElement( source );

	    firstScript.getParentNode().insertBefore( config, firstScript );
	}

	@Override
	public native void trackPageview() /*-{
		$wnd.ga('send', 'pageview');
	}-*/;

	@Override
	public native void trackPageview(String pageName) /*-{
		if (!pageName.match( "^/" ) == "/") {
			pageName = "/" + pageName;
		}
		$wnd.ga('send', 'pageview', pageName);
	}-*/;

	@Override
	public native void trackEvent(String category, String action) /*-{
		$wnd.ga('send', 'event', category, action);
	}-*/;

	@Override
	public native void trackEvent(String category, String action, String label) /*-{
		$wnd.ga('send', 'event', category, action, label);
	}-*/;

	@Override
	public native void trackEvent(String category, String action, String optLabel, int value) /*-{
		$wnd.ga('send', 'event', category, action, label, value);
	}-*/;
}
