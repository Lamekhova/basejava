package com.urise.storage.serialization;

import com.urise.model.*;
import com.urise.utill.XmlParser;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements Serializer{

    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        this.xmlParser = new XmlParser(
                Resume.class, TextSection.class, ListSection.class, ExperienceSection.class,
                ExperienceEntry.class, Position.class, Link.class
        );
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
