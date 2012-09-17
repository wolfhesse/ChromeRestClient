package org.rest.client.ui.desktop.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class QueryDetailRow extends Composite {

	private static QueryDetailRowUiBinder uiBinder = GWT
			.create(QueryDetailRowUiBinder.class);

	interface QueryDetailRowUiBinder extends UiBinder<Widget, QueryDetailRow> {
	}
	
	@UiField SimplePanel keyPanel;
	@UiField SimplePanel valuePanel;
	
	private final TextBox keyBox;
	private final TextBox valueBox;
	private final ClickHandler removeClickHandler;
	
	public QueryDetailRow(TextBox keyBox, TextBox valueBox, ClickHandler removeClickHandler) {
		initWidget(uiBinder.createAndBindUi(this));
		this.keyBox = keyBox;
		this.valueBox = valueBox;
		this.removeClickHandler = removeClickHandler;
		keyBox.getElement().setAttribute("placeholder", "key");
		valueBox.getElement().setAttribute("placeholder", "value");
		
		
		keyPanel.add(keyBox);
		valuePanel.add(valueBox);
		
		keyBox.getElement().focus();
	}
	
	
	public void setKeyValue(String keyValue){
		keyBox.setValue(keyValue);
	}
	
	public String getKeyValue(){
		return keyBox.getValue();
	}
	
	public void setValue(String value){
		valueBox.setValue(value);
	}
	
	public String getValue(){
		return valueBox.getValue();
	}
	
	@UiHandler("removeButton")
	void onRemove(ClickEvent e){
		this.removeFromParent();
		removeClickHandler.onClick(e);
	}
	
	@UiHandler("encodeButton")
	void onEncode(ClickEvent e){
		String value = getValue();
		if(value.trim().isEmpty()) return;
		
		if(e.isControlKeyDown()){
			value = URL.encodePathSegment(value);
		} else {
			value = URL.encodeQueryString(value);
		}
		valueBox.setValue(value, true);
	}
	@UiHandler("decodeButton")
	void onDecode(ClickEvent e){
		String value = getValue();
		if(value.trim().isEmpty()) return;
		
		if(e.isControlKeyDown()){
			value = URL.decodePathSegment(value);
		} else {
			value = URL.decodeQueryString(value);
		}
		valueBox.setValue(value, true);
	}
}