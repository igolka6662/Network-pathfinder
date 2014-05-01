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

public class Configuration {
	
	private static Configuration instance;
	
	public static final String RESOURCES_LOCATION = new File(".").getAbsolutePath()+"/resources/";
	public static final String CONGIGURATION_FILE_NAME = "configuration.xml";
	
	private Element allSettings = null;
	private String applicationLanguage = null;
	private String vertexsIcon = null;
	private String localizationFileName = null;
	
	private Configuration(){
		try {
			init();
		} catch (SAXException e) {
			System.out.println("Sax exception in Configuration");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException in Configuration");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfigurationException in Configuration");
			e.printStackTrace();
		}
		Localization.getInstance().setLang(getApplicationLanguage());
	}
	
	private void init() throws SAXException, IOException, ParserConfigurationException{
		File xmlConf = new File(RESOURCES_LOCATION+CONGIGURATION_FILE_NAME);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlConf);
		
		doc.getDocumentElement().normalize();
		
		NodeList settingsList = doc.getElementsByTagName("settings");
		Node settings = settingsList.item(0);
		if (settings.getNodeType() == Node.ELEMENT_NODE){
			allSettings = (Element) settings;
		}
	}
	
	public static Configuration getInstance(){
		if (instance == null){
			instance = new Configuration();
		}
		return instance;
	}

	public String getApplicationLanguage() {
		if (applicationLanguage == null){
			setApplicationLanguage(allSettings.getElementsByTagName("language").item(0).getTextContent());
		}
		return applicationLanguage;
	}

	public void setApplicationLanguage(String applicationLanguage) {
		this.applicationLanguage = applicationLanguage;
	}

	public String getVertexsIcon() {
		if (vertexsIcon == null){
			setVertexsIcon(allSettings.getElementsByTagName("vertex-icon").item(0).getTextContent());
		}
		return vertexsIcon;
	}

	public void setVertexsIcon(String vertexsIcon) {
		this.vertexsIcon = vertexsIcon;
	}

	public String getLocalizationFileName() {
		if (localizationFileName == null){
			setApplicationLanguage(allSettings.getElementsByTagName("localization-file").item(0).getTextContent());
		}
		return localizationFileName;
	}

	public void setLocalizationFileName(String localizationFileName) {
		this.localizationFileName = localizationFileName;
	}

}

