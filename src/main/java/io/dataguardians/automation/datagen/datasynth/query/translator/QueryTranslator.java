package io.dataguardians.automation.datagen.datasynth.query.translator;

import java.util.ArrayList;
import java.util.List;
import io.dataguardians.automation.datagen.datasynth.DataGenerator;
import io.dataguardians.automation.datagen.datasynth.GeneratorConfiguration;
import io.dataguardians.automation.datagen.datasynth.query.entity.FieldType;
import io.dataguardians.automation.datagen.datasynth.query.entity.QueryConfiguration;
import io.dataguardians.automation.datagen.datasynth.query.entity.QueryType;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.dataguardians.exceptions.HttpException;
import io.dataguardians.model.openai.Response;
import io.dataguardians.openai.GenerativeAPI;
import io.dataguardians.openai.endpoints.ChatApiEndpointRequest;
import io.dataguardians.security.ApiKey;
import io.dataguardians.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryTranslator extends DataGenerator<String> {

    private final QueryConfiguration queryConfig;

    public QueryTranslator(
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
        String query = "Generate a " + queryConfig.getQueryType() + " query that fulfills the following need " + queryConfig.getQuery() + " with the following data dictionary: " + queryConfig.getDataDictionary() + ". Only generate the resulting query.";
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

    public static void main(String [] args) throws HttpException, JsonProcessingException {
        TokenProvider provider = ApiKey.builder().fromEnv("OPENAI_API_KEY").build();

        GenerativeAPI chatGPT = new GenerativeAPI(provider);

        QueryConfiguration queryConfig = new QueryConfiguration();

        queryConfig.setQuery("I want to know the number of people who have a salary greater than 1000 whose first name is john");
        queryConfig.setQueryType(QueryType.LUCENE); // can be SQL or JEXl at this point
        List<QueryConfiguration.DataDictionaryDefinition> dict = new ArrayList<>();
        dict.add(QueryConfiguration.DataDictionaryDefinition.builder().type(FieldType.EXACT).fieldName("salary").build());
        dict.add(QueryConfiguration.DataDictionaryDefinition.builder().type(FieldType.EXACT).fieldName("person_name").build());
        queryConfig.setDataDictionary(dict);
        QueryTranslator translator = new QueryTranslator(provider, chatGPT, null,queryConfig);
        System.out.println(translator.generate());// response will be +person_name:john +salary:[1001 TO *]
    }
}
