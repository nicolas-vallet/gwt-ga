/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.nzv.gwt.ga.impl;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.Window;
import com.nzv.gwt.ga.GoogleAnalytics;

/**
 * Default {@link GoogleAnalytics} implementation that uses JSNI to
 * expose Google Analytics javascript methods.
 *
 * @author Christian Goudreau
 */
public class GoogleAnalyticsImpl
  implements GoogleAnalytics
{
  @Override
  public native void trackEvent( String category, String action ) /*-{
    $wnd._gaq.push( [ '_trackEvent', category, action ] );
  }-*/;

  @Override
  public native void trackEvent( String category, String action,
                                 String optLabel, int optValue ) /*-{
    $wnd._gaq.push( [ '_trackEvent', category, action, optLabel, optValue ] );
  }-*/;

  public native void trackPageview( String pageName ) /*-{
    if (!pageName.match( "^/" ) == "/") {
      pageName = "/" + pageName;
    }

    $wnd._gaq.push( [ '_trackPageview', pageName ] );
  }-*/;

  @Override
  public native void trackPageview() /*-{
    $wnd._gaq.push( [ '_trackPageview' ] );
  }-*/;

  @Override
  public void init( final String userAccount )
  {
    final Element firstScript = Document.get().getElementsByTagName( "script" ).getItem( 0 );
    final String source =
      "var _gaq = _gaq || [];_gaq.push(['_setAccount', '" + userAccount + "']);_gaq.push(['_trackPageview']);";
    final ScriptElement config = Document.get().createScriptElement( source );

    firstScript.getParentNode().insertBefore( config, firstScript );

    final ScriptElement script = Document.get().createScriptElement();

    // Add the google analytics script.
    script.setSrc( ( "https:".equals( Window.Location.getProtocol() )
                     ? "https://ssl" : "http://www" ) + ".google-analytics.com/ga.js" );
    script.setType( "text/javascript" );
    script.setAttribute( "async", "true" );

    firstScript.getParentNode().insertBefore( script, firstScript );
  }

  @Override
  public native void addAccount( String trackerName, String userAccount ) /*-{
    $wnd._gaq.push( [ '" + trackerName + "._setAccount', '" + userAccount + "' ] );
  }-*/;

  @Override
  public native void trackPageview( String trackerName, String pageName ) /*-{
    if (!pageName.match( "^/" ) == "/") {
      pageName = "/" + pageName;
    }

    $wnd._gaq.push( [ '" + trackerName + "._trackPageview', pageName ] );
  }-*/;

  @Override
  public native void trackEvent( String trackerName, String category, String action ) /*-{
    $wnd._gaq.push( [ '" + trackerName + "._trackEvent', category, action ] );
  }-*/;

  @Override
  public native void trackEvent( String trackerName, String category, String action,
                                 String optLabel, int optValue ) /*-{
    $wnd._gaq.push( [ '" + trackerName + "._trackEvent', category, action, optLabel, optValue ] );
  }-*/;
}
