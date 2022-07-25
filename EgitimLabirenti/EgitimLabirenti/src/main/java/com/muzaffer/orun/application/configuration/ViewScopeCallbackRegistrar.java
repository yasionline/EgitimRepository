package com.muzaffer.orun.application.configuration;

import javax.faces.component.UIViewRoot;
import javax.faces.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author muzaffer.orun
 *
 *	View Scope class ı
 *  Spring annotationlarında view scope olmadığı için eklendi
 *  jsf annotationlarındaki view scope un aynısı işlem yapmaktadır.
 *  Sayfa ilk açıldığında sayfa ve içindekileri cache e alır
 *  adres çubuğundan yeni bir sayfaya geçene kadar herşey tutulur.
 *
 */

public class ViewScopeCallbackRegistrar implements ViewMapListener {

	@SuppressWarnings("unchecked")
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if(event instanceof PostConstructViewMapEvent) {
			PostConstructViewMapEvent viewMapEvent = (PostConstructViewMapEvent)event;
			UIViewRoot viewRoot = (UIViewRoot)viewMapEvent.getComponent();
			viewRoot.getViewMap().put(ViewScope.VIEW_SCOPE_CALLBACKS,new HashMap<String,Runnable>());
		} else if(event instanceof PreDestroyViewMapEvent) {
			PreDestroyViewMapEvent viewMapEvent = (PreDestroyViewMapEvent)event;
			UIViewRoot viewRoot = (UIViewRoot)viewMapEvent.getComponent();
			Map<String,Runnable> callbacks = (Map<String, Runnable>) viewRoot.getViewMap().get(ViewScope.VIEW_SCOPE_CALLBACKS);
			if(callbacks != null) {
				for(Runnable c:callbacks.values()) {
					c.run();
				}
				callbacks.clear();
			}
		}
	}

	public boolean isListenerForSource(Object source) {
		return source instanceof UIViewRoot;
	}
}
