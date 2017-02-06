package bg.uni.sofia.fmi.web.scrum.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageBodyHandler<T> implements MessageBodyWriter<T>, MessageBodyReader<T> {

	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * A properly configured {@link Gson}
	 */
	public static final Gson gson = createGson();

	private static Gson createGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(YYYY_MM_DD_HH_MM_SS);
		return builder.create();
	}

	@Override
	public long getSize(final T t, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
		return true;
	}

	@Override
	public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
		return true;
	}

	@Override
	public T readFrom(final Class<T> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType,
			final MultivaluedMap<String, String> httpHeaders, final InputStream entityStream) throws IOException, WebApplicationException {
		T result = null;
		Type targetType;
		try (final Reader entityReader = new InputStreamReader(entityStream, StandardCharsets.UTF_8)) {
			if (Collection.class.isAssignableFrom(type)) {
				targetType = genericType;
			} else {
				targetType = type;
			}
			result = type.cast(gson.fromJson(entityReader, targetType));
		}
		return result;
	}

	@Override
	public void writeTo(final T t, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType,
			final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException, WebApplicationException {

		if (!String.class.isAssignableFrom(type)) {
			entityStream.write(gson.toJson(t).getBytes(StandardCharsets.UTF_8));
		} else {
			entityStream.write(((String) t).getBytes(StandardCharsets.UTF_8));
		}
	}
}