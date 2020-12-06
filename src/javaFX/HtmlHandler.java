package javaFX;

import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlHandler {
    public HtmlHandler() {
    }

    public static void editHtml(double lat, double lon) throws IOException {
        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter("src/javaFX/leafletWithMarker.html");
            out = new BufferedWriter(fstream);
            out.write("<html>\n" +
                    "<head>\n" +
                    "  <title>A Leaflet map!</title>\n" +
                    "  <link rel=\"stylesheet\" href=\"http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css\"/>\n" +
                    "  <script src=\"http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js\"></script>\n" +
                    "\n" +
                    "<script src=\"79_SubaruStretch.js\"></script>\n" +
                    "\n" +
                    "\n" +
                    "  <style>\n" +
                    "    #map{ height: 100% }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "  <div id=\"map\"></div>\n" +
                    "\n" +
                    "  <script type=\"text/javascript\">\n" +
                    "\n" +
                    "\tvar map = L.map('map').setView([32, 34], 2);  \n" +
                    "\tL.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map); \n" +
                    "    var marker = L.marker([" + Double.toString(lat) + ", " + Double.toString(lon) + "]).addTo(map);\n" +
                    "\n" +
                    "    function onEachFeature(feature, layer) {\n" +
                    "        if (feature.properties) {\n" +
                    "        layer.bindPopup(\"<b>\" + feature.properties.phase + \"</b> from\" + feature.properties.from + \" to \" + feature.properties.to);\n" +
                    "        }\n" +
                    "    }\n" +
                    "    //map.tap.disable();\n" +
                    "\n" +
                    "    var streets = new L.geoJson(roads, {\n" +
                    "\t\tonEachFeature: onEachFeature,\n" +
                    "\t\tstyle: function(feature) { return {color: feature.properties.color}; }\n" +
                    "\t}).addTo(map);\n" +
                    "\n" +
                    "  </script>\n" +
                    "</body>\n" +
                    "</html>");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error!");
            alert.setHeaderText(null);
            alert.setContentText("error: " + e.getMessage());
            alert.showAndWait();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    public static void startMapHtml() throws IOException {
        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter("src/javaFX/leafletWithMarker.html");
            out = new BufferedWriter(fstream);
            out.write("<html>\n" +
                    "<head>\n" +
                    "  <title>A Leaflet map!</title>\n" +
                    "  <link rel=\"stylesheet\" href=\"http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css\"/>\n" +
                    "  <script src=\"http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js\"></script>\n" +
                    "\n" +
                    "<script src=\"79_SubaruStretch.js\"></script>\n" +
                    "\n" +
                    "\n" +
                    "  <style>\n" +
                    "    #map{ height: 100% }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "  <div id=\"map\"></div>\n" +
                    "\n" +
                    "  <script type=\"text/javascript\">\n" +
                    "\n" +
                    "\tvar map = L.map('map').setView([32, 34], 2);  \n" +
                    "\tL.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map); \n" +
                    "\n" +
                    "    function onEachFeature(feature, layer) {\n" +
                    "        if (feature.properties) {\n" +
                    "        layer.bindPopup(\"<b>\" + feature.properties.phase + \"</b> from\" + feature.properties.from + \" to \" + feature.properties.to);\n" +
                    "        }\n" +
                    "    }\n" +
                    "    //map.tap.disable();\n" +
                    "\n" +
                    "    var streets = new L.geoJson(roads, {\n" +
                    "\t\tonEachFeature: onEachFeature,\n" +
                    "\t\tstyle: function(feature) { return {color: feature.properties.color}; }\n" +
                    "\t}).addTo(map);\n" +
                    "\n" +
                    "  </script>\n" +
                    "</body>\n" +
                    "</html>");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error!");
            alert.setHeaderText(null);
            alert.setContentText("error: " + e.getMessage());
            alert.showAndWait();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
