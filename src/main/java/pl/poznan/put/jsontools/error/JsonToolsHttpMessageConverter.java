package pl.poznan.put.jsontools.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonToolsHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    @Bean
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new JsonToolsHttpMessageConverter();
    }

    private ObjectMapper initiatePrettyObjectMapper() {
        ObjectMapper customObjectMapper = new ObjectMapper();
        customObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // additional indentation for arrays
        DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
        pp.indentArraysWith(new DefaultIndenter());
        customObjectMapper.setDefaultPrettyPrinter(pp);

        return customObjectMapper;
    }

    @Override
    public Object read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        JavaType javaType = getJavaType(type, contextClass);
        return customReadJavaType(javaType, inputMessage);
    }

    private Object customReadJavaType(JavaType javaType, HttpInputMessage inputMessage) throws IOException {
        try {
            if (inputMessage instanceof MappingJacksonInputMessage) {
                Class<?> deserializationView = ((MappingJacksonInputMessage) inputMessage).getDeserializationView();
                if (deserializationView != null) {
                    return this.objectMapper.readerWithView(deserializationView).forType(javaType).readValue(inputMessage.getBody());
                }
            }
            return this.objectMapper.readValue(inputMessage.getBody(), javaType);
        } catch (InvalidDefinitionException ex) {
            throw new HttpMessageConversionException("Type definition error: " + ex.getType(), ex);
        } catch (JsonProcessingException ex) {
            //throw new HttpMessageNotReadableException("JSON parse error: " + ex.getOriginalMessage(), ex, inputMessage);
            throw new JsonToolsInvalidJsonError();
        }
    }
}
