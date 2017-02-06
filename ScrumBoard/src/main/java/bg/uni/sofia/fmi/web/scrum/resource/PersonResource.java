package bg.uni.sofia.fmi.web.scrum.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import bg.uni.sofia.fmi.web.scrum.dao.AbstractDao;
import bg.uni.sofia.fmi.web.scrum.dao.PersonDao;
import bg.uni.sofia.fmi.web.scrum.model.Person;

@Path("/member")
public class PersonResource extends AbstractResource<Long, Person> {

	private PersonDao personDao = new PersonDao();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllMembers(){
		return Response.ok(super.findAll()).build();
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(Person person, @Context UriInfo uriInfo) {
		URI newPersonURI = super.create(person, uriInfo);
		return Response.created(newPersonURI).build();
	}

	@Override
	protected AbstractDao<Long, Person> getDao() {
		return this.personDao;
	}

}
