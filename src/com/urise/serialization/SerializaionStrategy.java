package com.urise.serialization;

import java.io.IOException;

public interface SerializaionStrategy<T, SI, SO> {

    public void doWrite(T resume, SO outputStream) throws IOException;
    public T doRead(SI inputStream) throws IOException;
}
