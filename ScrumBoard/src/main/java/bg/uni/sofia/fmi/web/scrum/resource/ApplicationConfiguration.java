package bg.uni.sofia.fmi.web.scrum.resource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ApplicationConfiguration extends Application {

	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(TaskResource.class, PersonResource.class, MessageBodyHandler.class));
	}
}
