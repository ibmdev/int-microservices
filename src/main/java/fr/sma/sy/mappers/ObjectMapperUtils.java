package fr.sma.sy.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.asm.TypeReference;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ObjectMapperUtils {

    private static ModelMapper modelMapper = new ModelMapper();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private ObjectMapperUtils() {}
    public static  <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }
    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }
    public static <D> D mapJson(final String entity, Class<D> outClass)  {
        try {
            log.debug("mapJson > Mapping Flux JSON : " + entity + "vers la classe : " + outClass.getName());
            return objectMapper.readValue(entity, outClass);
        }
        catch(Throwable t) {
            log.error("mapJson > Erreur de parsing du flux json " + entity + "vers la classe : " + outClass.getName());
            return null;
        }
    }

    public static <T> List<T> mapJsonAsList(final String entity, Class<T> outClass)  {
        try {
            log.debug("mapJsonAsList > Mapping Flux JSON : " + entity + "vers la classe : " + outClass.getName());
            Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + outClass.getName() + ";");
            T[] objects = objectMapper.readValue(entity, arrayClass);
            return Arrays.asList(objects);
        }
        catch(Throwable t) {
            log.error("mapJsonAsList > Erreur de parsing du flux json " + entity + "vers la classe : " + outClass.getName());
            return null;
        }
    }
}
