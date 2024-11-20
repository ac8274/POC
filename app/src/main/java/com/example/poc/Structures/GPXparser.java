package com.example.poc.Structures;

import android.location.Location;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class GPXparser {

    private XmlSerializer serializer;

    public GPXparser(File file)
    {
        this.serializer = Xml.newSerializer();
        try {
            FileOutputStream fous = new FileOutputStream(file);
            this.serializer.setOutput(fous,"UTF-8");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startWriting() throws IOException {
        this.serializer.startDocument("UTF-8",false);
        this.serializer.startTag(null,"gpx");
        this.serializer.attribute(null,"xmlns","http://www.topografix.com/GPX/1/1");
        this.serializer.attribute(null,"version","1.1");
        this.serializer.attribute(null,"creator","Cats_army");
        this.serializer.attribute(null,"xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        this.serializer.attribute(null,"xsi:schemaLocation","http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd");
        //First part of XML FILEmost important for setting up which kind of file this is.

        this.serializer.startTag(null,"metadata");
        this.serializer.startTag(null,"name").text("POC test").endTag(null,"name");
        this.serializer.startTag(null,"desc").text("Proof of concept GPX file").endTag(null,"desc");
        this.serializer.startTag(null,"author");
        this.serializer.startTag(null,"name").text("Arie Chernobilsky").endTag(null,"name");
        this.serializer.endTag(null,"author");
        this.serializer.endTag(null,"metadata");
        //Second part finished, This part is of the Author writes.
    }

    public void addPoint(Location location) throws IOException {
        this.serializer.startTag(null,"wpt");
        this.serializer.attribute(null,"lat",String.valueOf(location.getLatitude()));
        this.serializer.attribute(null,"lon",String.valueOf(location.getLongitude()));
        this.serializer.startTag(null,"ele").text(String.valueOf(location.getAltitude())).endTag(null,"ele");

    }
}
