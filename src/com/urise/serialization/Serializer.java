package com.urise.serialization;

import com.urise.model.Resume;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {

    void doWrite(Resume resume, OutputStream outputStream) throws IOException;
    Resume doRead(InputStream inputStream) throws IOException;
}
