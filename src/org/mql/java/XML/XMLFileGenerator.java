package org.mql.java.XML;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mql.java.reflection.ClassParser;
import org.mql.java.reflection.PackageExplorer;
import org.mql.java.reflection.ProjectExplorer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLFileGenerator {
	private String projectName;
	 

	public XMLFileGenerator(String projectName) {
		this.projectName=projectName;
		fct();
	}
	
	public void fct() {
		try {
			ProjectExplorer projectExplorer = new ProjectExplorer(projectName);
			PackageExplorer packageExplorer = new PackageExplorer(projectName);
			Set<String> packageList = new HashSet<>();
			projectExplorer.listOfPackage(projectName, packageList);
			
			// Création d'une instance de DocumentBuilder
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    
		    // Création d'un document XML
		    Document document = builder.newDocument();
		    
		    // Création de  la racine de l'arbre XML
		    Element racine = document.createElement("project");
		    racine.setAttribute("projectName", projectName);
		    document.appendChild(racine);
		    
		    for (String packg : packageList) {
		    	Element pack = document.createElement("package");
		    	pack.setAttribute("packageName", packg);
		    	racine.appendChild(pack);
		    	Set<String> classList = new HashSet<String>();
		    	packageExplorer.getClassList(packg, classList);
		    	for(String cls : classList) {
		    		Element clazz = document.createElement("class");
		    		clazz.setAttribute("className", cls);
		    		pack.appendChild(clazz);
		    		
		    		Class<?> c=  null ;// packageExplorer.loadClass(cls);
					ClassParser classparser = new ClassParser(c);
					String clsm =classparser.getClassModifier();
					
					Element classmodifier = document.createElement("classModifier");
					classmodifier.setTextContent(clsm);
					clazz.appendChild(classmodifier);
					
					Element attributs = document.createElement("attributes");
					clazz.appendChild(attributs);
					Set<Field> listFields = classparser.getFieldList();
					for (Field field : listFields) {
						Element attribut = document.createElement("attribut");
						attributs.appendChild(attribut);
						
						Element attributeModifier = document.createElement("attributeModifier");
						attributeModifier.setTextContent(classparser.getFieldModificateur(field));
						attribut.appendChild(attributeModifier);
						
						Element attributeType = document.createElement("attributeType");
						attribut.appendChild(attributeType);
						
						Element fieldName = document.createElement("fieldName");
						fieldName.setTextContent(field.getName());
						attribut.appendChild(fieldName);
						
						if(Collection.class.isAssignableFrom(field.getType())) {
							
							
							
							ParameterizedType genericType =(ParameterizedType) field.getGenericType();
							Class<?> elmntType =(Class<?>) genericType.getActualTypeArguments()[0];
							
							Element collection = document.createElement("collection");
							attributeType.appendChild(collection);
							
							String cType = field.getType().getSimpleName();
							Element collectionType = document.createElement("collectionType");
							collectionType.setTextContent(cType);
							collection.appendChild(collectionType);
							
							Element ElementType = document.createElement("elementType");
							ElementType.setTextContent(elmntType.getSimpleName());
							collection.appendChild(ElementType);
							
							
						}else {
							attributeType.setTextContent(field.getType().getSimpleName());
						}					
					}
					//__________________________________________________________//
					Element methods = document.createElement("methodes");
					clazz.appendChild(methods);
		    		Set<Method> listMethods =  new HashSet<>();
		    		listMethods = classparser.getMethodsList();
		    		for (Method method : listMethods) {
		    			
		    			Element methd = document.createElement("method");
		    			methods.appendChild(methd);
		    			
		    			Element methodeModifier = document.createElement("methodeModifier");
		    			methd.appendChild(methodeModifier);
		    			methodeModifier.setTextContent(classparser.getMethodeModifier(method));
		    			
		    			Element returnType = document.createElement("returnType");
		    			methd.appendChild(returnType);
		    			
		    			if(Collection.class.isAssignableFrom(method.getReturnType())){
		    				ParameterizedType MparameterizedType = (ParameterizedType) method.getGenericReturnType();
		    				Class<?> MelmntType =(Class<?>) MparameterizedType.getActualTypeArguments()[0];
		    				
		    				Element Mcollection = document.createElement("collection");
		    				returnType.appendChild(Mcollection);
		    				
		    				String McType = method.getReturnType().getSimpleName();
		    				Element McollectionType = document.createElement("collectionType");
		    				McollectionType.setTextContent(McType);
		    				Mcollection.appendChild(McollectionType);
		    				
		    				Element MelementType = document.createElement("elementType");
		    				MelementType.setTextContent(MelmntType.getSimpleName());
		    				Mcollection.appendChild(MelementType);
		    				
		    			} else {
		    				returnType.setTextContent(method.getReturnType().getSimpleName());
		    			}
		    			Element methodeName = document.createElement("methodeName");
		    			methodeName.setTextContent(method.getName());
		    			methd.appendChild(methodeName);
		    			
		    			
		    
		    			
					}
					}
		    		
		    	}
		    	
			
		    
		    TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT,"yes");
		    DOMSource source = new DOMSource(document);
		    StreamResult result = new StreamResult("resources/fichier.xml");
		    transformer.transform(source, result);	
			
		} catch (Exception e) {}
		
		
	}
	
	public static void main(String[] args) {
		new XMLFileGenerator("C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams generator\\bin\\");
	}

}
