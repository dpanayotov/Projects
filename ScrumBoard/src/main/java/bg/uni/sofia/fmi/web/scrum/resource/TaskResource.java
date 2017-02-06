package bg.uni.sofia.fmi.web.scrum.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import bg.uni.sofia.fmi.web.scrum.dao.AbstractDao;
import bg.uni.sofia.fmi.web.scrum.dao.PersonDao;
import bg.uni.sofia.fmi.web.scrum.dao.TaskDao;
import bg.uni.sofia.fmi.web.scrum.model.Person;
import bg.uni.sofia.fmi.web.scrum.model.Task;

@Path("/task")
public class TaskResource extends AbstractResource<Long, Task> {

	private TaskDao taskDao = new TaskDao();
	private PersonDao personDao = new PersonDao();

	@GET
	public Response findAllTasks() {
		return Response.ok(super.findAll()).build();
	}

	@GET
	@Path("{id}")
	public Response findTaskById(@PathParam("id") Long id) {
		Task task = super.findById(id);
		return Response.ok(task).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTask(@Context UriInfo uriInfo, Task task) {
		URI newEntityUri = super.create(task, uriInfo);
		return Response.created(newEntityUri).build();
	}

	@PUT
	@Path("/process")
	public Response processTask(@QueryParam("processorId") Long processorId, @QueryParam("taskId") Long taskId) {
		Task task = super.findById(taskId);
		task.progress();
		Person processor = personDao.findById(processorId);
		processor.getTasks().add(task);
		Task updatedTask = super.update(task);
		return Response.ok(updatedTask).build();
	}

	@Override
	protected AbstractDao<Long, Task> getDao() {
		return this.taskDao;
	}
}
