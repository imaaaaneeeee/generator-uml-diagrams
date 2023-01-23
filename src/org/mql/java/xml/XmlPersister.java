package org.mql.java.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mql.java.models.ClassModel;
import org.mql.java.models.FieldModel;
import org.mql.java.models.MethodeModel;
import org.mql.java.models.PackageModel;
import org.mql.java.models.ProjectModel;
import org.mql.java.models.RelationModel;
import org.mql.java.models.TypeModel;
import org.mql.java.reflection.ProjectExplorer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlPersister {

	public XmlPersister() {
		// TODO Auto-generated constructor stub
	}

	 public static void saveToXml(String projectDirectory, String fileName) {
	        ProjectExplorer projectexplorer = new ProjectExplorer(projectDirectory);
	        ProjectModel projectModel = projectexplorer.exploreProject(projectDirectory);

	        try {
	            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	            Document doc = docBuilder.newDocument();
	            Element root = createRootElement(doc, projectModel);
	            doc.appendChild(root);
	            Element packagesElement = createPackagesElement(doc, projectModel);
	            root.appendChild(packagesElement);
	            for (PackageModel packageModel : projectModel.getPackages()) {
	                Element packageElement = createPackageElement(doc, packageModel);
	                packagesElement.appendChild(packageElement);
	                Element classes = createClassesElement(doc, packageModel);
	                packageElement.appendChild(classes);
	                for (ClassModel classModel : packageModel.getClasses()) {
	                    Element classElement = createClassElement(doc, classModel);
	                    classes.appendChild(classElement);
	                    Element fields = createFieldsElement(doc, classModel);
	                    classElement.appendChild(fields);
	                    for (FieldModel field : classModel.getFields()) {
	                        Element fieldElement = createFieldElement(doc, field);
	                        fields.appendChild(fieldElement);
	                    }
	                    Element methods = createMethodsElement(doc, classModel);
	                    classElement.appendChild(methods);
	                    for (MethodeModel method : classModel.getMethods()) {
	                        Element methodElement = createMethodElement(doc, method);
	                        methods.appendChild(methodElement);
	                    }
	                }
	                Element relations = createRelationsElement(doc, packageModel);
	                packageElement.appendChild(relations);
	                for (RelationModel relationModel : packageModel.getRelation()) {
	                    Element relationElement = createRelationElement(doc, relationModel);
	                    relations.appendChild(relationElement);
	                }
	            }
	            // write the content into xml file
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(fileName));
				transformer.transform(source, result);


	        } catch (Exception pce) {
	            pce.printStackTrace();
	        }
	    }
	
	   private static Element createRootElement(Document doc, ProjectModel projectModel) {
	        Element root = doc.createElement("project");
	        root.setAttribute("name", projectModel.getName());
	        return root;
	    }

	    private static Element createPackagesElement(Document doc, ProjectModel projectModel) {
	        Element packagesElement = doc.createElement("packages");
	        return packagesElement;
	    }
	
	 private static Element createPackageElement(Document doc, PackageModel packageModel) {
	        Element packageElement = doc.createElement("package");
	        packageElement.setAttribute("name", packageModel.getPackageName());
	        return packageElement;
	    }

	    private static Element createClassesElement(Document doc, PackageModel packageModel) {
	        Element classes = doc.createElement("classes");
	        return classes;
	    }

	    private static Element createClassElement(Document doc, ClassModel classModel) {
	        Element classElement;
	        if (classModel.isInterface()) {
	            classElement = doc.createElement("interface");
	        } else {
	            classElement = doc.createElement("class");
	        }
	        classElement.setAttribute("name", classModel.getSimpleName());
	        return classElement;
	    }

	    private static Element createFieldsElement(Document doc, ClassModel classModel) {
	        Element fields = doc.createElement("fields");
	        return fields;
	    }

	    private static Element createFieldElement(Document doc, FieldModel field) {
	        Element fieldElement = doc.createElement("field");
	        fieldElement.setAttribute("name", field.getName());
	        fieldElement.setAttribute("modifier", field.getModifier());
	        fieldElement.setAttribute("type", field.getType().getTypeName());
	        return fieldElement;
	    }

	    private static Element createMethodsElement(Document doc, ClassModel classModel) {
	        Element methods = doc.createElement("methods");
	        return methods;
	    }

	    private static Element createMethodElement(Document doc, MethodeModel method) {
	        Element methodElement = doc.createElement("method");
	        methodElement.setAttribute("name", method.getName());
	        methodElement.setAttribute("modifier", method.getModifier());
	        methodElement.setAttribute("returnType", method.getReturnType().getTypeName());

	        Element parametersElement = doc.createElement("parameters");
	        for (TypeModel type : method.getParameterInfo()) {
	            Element parameter = doc.createElement("parameter");
	            parameter.setAttribute("type", type.getType().getTypeName());
	            parametersElement.appendChild(parameter);
	        }
	        methodElement.appendChild(parametersElement);
	        return methodElement;
	    }

	    private static Element createRelationsElement(Document doc, PackageModel packageModel) {
	        Element relations = doc.createElement("relations");
	        return relations;
	    }

	    private static Element createRelationElement(Document doc, RelationModel relationModel) {
	        Element relationElement = doc.createElement("relation");
	        relationElement.setAttribute("related-Class", relationModel.getRelatedClass());
			relationElement.setAttribute("related-With", relationModel.getRelatedWith().toString());
			relationElement.setAttribute("relation-type", relationModel.getRelation().toString());
	        return relationElement;
	}
	
	
	
	
	

	public static void main(String[] args) {
		XmlPersister.saveToXml("C:\\Users\\Dell\\eclipse-workspace\\UML Diagrams generator\\bin\\",
				"resources/project.xml");
	}

}
