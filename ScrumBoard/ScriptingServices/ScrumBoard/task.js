/* globals $ */
/* eslint-env node, dirigible */

var entityTask = require('ScrumBoard/task_lib');
var request = require("net/http/request");
var response = require("net/http/response");
var xss = require("utils/xss");

handleRequest();

function handleRequest() {
	
	response.setContentType("application/json; charset=UTF-8");
	response.setCharacterEncoding("UTF-8");
	
	// get method type
	var method = request.getMethod();
	method = method.toUpperCase();
	
	//get primary keys (one primary key is supported!)
	var idParameter = entityTask.getPrimaryKey();
	
	// retrieve the id as parameter if exist 
	var id = xss.escapeSql(request.getAttribute("path"));
	if (!id) {
		id = xss.escapeSql(request.getParameter(idParameter));
	}
	var count = xss.escapeSql(request.getParameter('count'));
	var metadata = xss.escapeSql(request.getParameter('metadata'));
	var sort = xss.escapeSql(request.getParameter('sort'));
	var limit = xss.escapeSql(request.getParameter('limit'));
	var offset = xss.escapeSql(request.getParameter('offset'));
	var desc = xss.escapeSql(request.getParameter('desc'));
	
	if (limit === null) {
		limit = 100;
	}
	if (offset === null) {
		offset = 0;
	}
	
	if(!entityTask.hasConflictingParameters(id, count, metadata)) {
		// switch based on method type
		if ((method === 'POST')) {
			// create
			entityTask.createTask();
		} else if ((method === 'GET')) {
			// read
			if (id) {
				entityTask.readTaskEntity(id);
			} else if (count !== null) {
				entityTask.countTask();
			} else if (metadata !== null) {
				entityTask.metadataTask();
			} else {
				entityTask.readTaskList(limit, offset, sort, desc);
			}
		} else if ((method === 'PUT')) {
			// update
			entityTask.updateTask();    
		} else if ((method === 'DELETE')) {
			// delete
			if(entityTask.isInputParameterValid(idParameter)){
				entityTask.deleteTask(id);
			}
		} else {
			entityTask.printError(response.BAD_REQUEST, 4, "Invalid HTTP Method", method);
		}
	}
	
	// flush and close the response
	response.flush();
	response.close();
}