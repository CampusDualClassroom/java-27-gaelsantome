package com.campusdual.classroom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;



public class Exercise27 {


    private static void createXML() throws ParserConfigurationException, TransformerException {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Crear el documento XML
            Element root = document.createElement("shoppinglist");
            document.appendChild(root);

            Element items = document.createElement("items");
            root.appendChild(items);

            // Lista de artículos
            items.appendChild(createItem(document, "item", "quantity", "2", "Manzanas"));
            items.appendChild(createItem(document, "item", "quantity", "1", "Leche"));
            items.appendChild(createItem(document, "item", "quantity", "3", "Pan"));
            items.appendChild(createItem(document, "item", "quantity", "2", "Huevo"));
            items.appendChild(createItem(document, "item", "quantity", "1", "Queso"));
            items.appendChild(createItem(document, "item", "quantity", "1", "Cereal"));
            items.appendChild(createItem(document, "item", "quantity", "4", "Agua"));
            items.appendChild(createItem(document, "item", "quantity", "6", "Yogur"));
            items.appendChild(createItem(document, "item", "quantity", "2", "Arroz"));
            writeToFile(document, "src/main/resources/shoppingList.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(Document document, String pathFile) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt indent-amount", "3");
        File file = new File(pathFile);
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }

    private static Element createItem(Document document, String tagName, String attribute, String attrValue, String textComponent) {
        Element item = document.createElement(tagName);
        item.setAttribute(attribute, attrValue);
        item.setTextContent(textComponent);
        return item;
    }

    private static void createJSON() {

        JsonObject jsonObject = new JsonObject();
        JsonArray itemsArray = new JsonArray();

        itemsArray.add(Exercise27.createItemJson(2, "Manzana"));
        itemsArray.add(Exercise27.createItemJson(1, "Leche"));
        itemsArray.add(Exercise27.createItemJson(3, "Pan"));
        itemsArray.add(Exercise27.createItemJson(2, "Huevo"));
        itemsArray.add(Exercise27.createItemJson(1, "Queso"));
        itemsArray.add(Exercise27.createItemJson(1, "Cereal"));
        itemsArray.add(Exercise27.createItemJson(4, "Agua"));
        itemsArray.add(Exercise27.createItemJson(6, "Yogur"));
        itemsArray.add(Exercise27.createItemJson(2, "Arroz"));

        jsonObject.add("items", itemsArray);

        try (FileWriter fw = new FileWriter("src/main/resources/shoppingList.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(jsonObject);
            fw.write(json);
            fw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static JsonObject createItemJson (int quantity, String ItemDesc){

        JsonObject item= new JsonObject();

        item.addProperty("quantity", quantity);
        item.addProperty("text", ItemDesc);
        return item;

    }

    public static void main(String[] args) {
        try{
            Exercise27.createXML();
        }catch (ParserConfigurationException | TransformerException e){
            throw new RuntimeException(e);
        }
        Exercise27.createJSON();
    }

}
