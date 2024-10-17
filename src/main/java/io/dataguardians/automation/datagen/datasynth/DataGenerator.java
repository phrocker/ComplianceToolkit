package io.dataguardians.automation.datagen.datasynth;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.dataguardians.exceptions.HttpException;
import io.dataguardians.openai.GenerativeAPI;
import io.dataguardians.security.TokenProvider;

/**
 * Data generator base class.
 * @param <T>
 */
public abstract class DataGenerator<T> {

    protected final TokenProvider token;
    protected final GenerativeAPI api;

    protected final GeneratorConfiguration config;

    public DataGenerator(TokenProvider token, GenerativeAPI generator, GeneratorConfiguration config) {
        this.token = token;
        this.api = generator;
        this.config = config;
    }

    protected abstract String generateInput();

    public abstract T generate() throws HttpException, JsonProcessingException;

}
