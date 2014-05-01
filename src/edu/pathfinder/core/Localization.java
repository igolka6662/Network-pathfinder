package edu.pathfinder.core;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Localization {
	
	private static Localization instance = null;
	private static String lang = null;
	private boolean isInit = false;
	private NodeList settingsList = null;
	
	private Localization(){
		lang = Constants.LOCAL_EN;
	}
	
	public static Localization getInstance(){
		if (instance == null){
			instance = new Localization();
		}
		return instance;
	}
	
	public String getLocalizedString(String word, String lang){
		if (!isInit){
			File xmlConf = new File(Constants.RESOURCES_LOCATION+"local.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				System.err.println("Parse error before getting localization string");
			}
			Document doc = null;
			try {
				doc = dBuilder.parse(xmlConf);
			} catch (SAXException e) {
				System.err.println("SAXException before getting localization string");
			} catch (IOException e) {
				System.err.println("IOException before getting localization string");
			}
			
			doc.getDocumentElement().normalize();
			
			settingsList = doc.getElementsByTagName("item");
			isInit = true;
		}
		String localizedString = null;
		for (int i=0; i<settingsList.getLength(); i++){
			Node settings = settingsList.item(i);
			if (settings.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element) settings;
				if (word.equals(element.getAttribute("name"))){
					localizedString = element.getElementsByTagName(lang).item(0).getTextContent();
				}
			}
		}
		
		return localizedString != null ? localizedString : "<Empty>" ;
	}
	
	public String getLocalizedString(String word){
		return getLocalizedString(word, lang);
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
}
