package io.dataguardians.automation.datagen.datasynth.query.explainer;

import io.dataguardians.automation.datagen.datasynth.DataGenerator;
import io.dataguardians.automation.datagen.datasynth.GeneratorConfiguration;
import io.dataguardians.automation.datagen.datasynth.query.entity.QueryConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.dataguardians.exceptions.HttpException;
import io.dataguardians.model.openai.Response;
import io.dataguardians.openai.GenerativeAPI;
import io.dataguardians.openai.endpoints.ChatApiEndpointRequest;
import io.dataguardians.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryExplainer extends DataGenerator<String> {

    private final QueryConfiguration queryConfig;

    public QueryExplainer(
        TokenProvider token, GenerativeAPI generator, GeneratorConfiguration config,
        QueryConfiguration queryConfig) {
        super(token, generator, config);
        this.queryConfig = queryConfig;
    }

    /**
     * Generates input for the generative AI endpoint.
     *
     * @return Question to be asked to the generative AI endpoint.
     */
    @Override
    protected String generateInput() {
        String query = "Explain this query " + queryConfig.getQuery();
        return query;
    }


    /**
     * Generates a list of Strings by HTTP request and JSON processing.
     *
     * @return the list of Strings generated
     *
     * @throws HttpException
     *             if there is a problem with the HTTP request
     * @throws JsonProcessingException
     *             if there is a problem processing the JSON
     */
    @Override
    public String generate() throws HttpException, JsonProcessingException {
        ChatApiEndpointRequest request = ChatApiEndpointRequest.builder().input(generateInput()).maxTokens(1024)
                .build();
        Response hello = api.sample(request, Response.class);
        return hello.concatenateResponses();
    }
}
