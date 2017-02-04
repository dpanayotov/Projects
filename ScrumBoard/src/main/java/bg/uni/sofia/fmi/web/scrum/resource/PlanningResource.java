package bg.uni.sofia.fmi.web.scrum.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import bg.uni.sofia.fmi.web.scrum.dao.AbstractDao;
import bg.uni.sofia.fmi.web.scrum.dao.PlanningDao;
import bg.uni.sofia.fmi.web.scrum.model.Planning;

@Path("/planning")
public class PlanningResource extends AbstractResource<Long, Planning> {

	@Context
	private UriInfo uriInfo;

	private PlanningDao planningDao = new PlanningDao();

	@GET
	@Path("{id}")
	public Response findPlanningById(@PathParam("id") Long id) {
		Planning planning = super.findById(id);
		return Response.ok(planning).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPlanning(Planning planning) {
		super.create(planning);
		String uri = uriInfo.getAbsolutePath().toString();
		URI newEntityUri = UriBuilder.fromPath(uri + "/" + planning.getKey()).build();
		return Response.created(newEntityUri).build();
	}

	@Override
	protected AbstractDao<Long, Planning> getDao() {
		return this.planningDao;
	}
}
