/* globals $ */
/* eslint-env node, dirigible */

var request = require("net/http/request");
var response = require("net/http/response");
var database = require("db/database");
var xss = require("utils/xss");

var datasource = database.getDatasource();

// create entity by parsing JSON object from request body
exports.createTask = function() {
    var input = request.readInputText();
    var requestBody = JSON.parse(input);
    var connection = datasource.getConnection();
    try {
        var sql = "INSERT INTO TASK (";
        sql += "ID";
        sql += ",";
        sql += "NAME";
        sql += ",";
        sql += "DESCRIPTION";
        sql += ",";
        sql += "ASSIGNEE";
        sql += ",";
        sql += "PRIORITY";
        sql += ") VALUES ("; 
        sql += "?";
        sql += ",";
        sql += "?";
        sql += ",";
        sql += "?";
        sql += ",";
        sql += "?";
        sql += ",";
        sql += "?";
        sql += ")";

        var statement = connection.prepareStatement(sql);
        var i = 0;
        var id = datasource.getSequence('TASK_ID').next();
        statement.setInt(++i, id);
        statement.setString(++i, requestBody.name);
        statement.setString(++i, requestBody.description);
        statement.setString(++i, requestBody.assignee);
        statement.setInt(++i, requestBody.priority);
        statement.executeUpdate();
		response.println(id);
        return id;
    } catch(e) {
        var errorCode = response.BAD_REQUEST;
        exports.printError(errorCode, errorCode, e.message, sql);
    } finally {
        connection.close();
    }
    return -1;
};

// read single entity by id and print as JSON object to response
exports.readTaskEntity = function(id) {
    var connection = datasource.getConnection();
    try {
        var result;
        var sql = "SELECT * FROM TASK WHERE " + exports.pkToSQL();
        var statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        
        var resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result = createEntity(resultSet);
        } else {
        	exports.printError(response.NOT_FOUND, 1, "Record with id: " + id + " does not exist.", sql);
        }
        var jsonResponse = JSON.stringify(result, null, 2);
        response.println(jsonResponse);
    } catch(e){
        var errorCode = response.BAD_REQUEST;
        exports.printError(errorCode, errorCode, e.message, sql);
    } finally {
        connection.close();
    }
};

// read all entities and print them as JSON array to response
exports.readTaskList = function(limit, offset, sort, desc) {
    var connection = datasource.getConnection();
    try {
        var result = [];
        var sql = "SELECT ";
        if (limit !== null && offset !== null) {
            sql += " " + datasource.getPaging().genTopAndStart(limit, offset);
        }
        sql += " * FROM TASK";
        if (sort !== null) {
            sql += " ORDER BY " + sort;
        }
        if (sort !== null && desc !== null) {
            sql += " DESC ";
        }
        if (limit !== null && offset !== null) {
            sql += " " + datasource.getPaging().genLimitAndOffset(limit, offset);
        }
        var statement = connection.prepareStatement(sql);
        var resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result.push(createEntity(resultSet));
        }
        var jsonResponse = JSON.stringify(result, null, 2);
        response.println(jsonResponse);
    } catch(e){
        var errorCode = response.BAD_REQUEST;
        exports.printError(errorCode, errorCode, e.message, sql);
    } finally {
        connection.close();
    }
};

//create entity as JSON object from ResultSet current Row
function createEntity(resultSet) {
    var result = {};
	result.id = resultSet.getInt("ID");
    result.name = resultSet.getString("NAME");
    result.description = resultSet.getString("DESCRIPTION");
    result.assignee = resultSet.getString("ASSIGNEE");
	result.priority = resultSet.getInt("PRIORITY");
    return result;
}

function convertToDateString(date) {
    var fullYear = date.getFullYear();
    var month = date.getMonth() < 10 ? "0" + date.getMonth() : date.getMonth();
    var dateOfMonth = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    return fullYear + "/" + month + "/" + dateOfMonth;
}

// update entity by id
exports.updateTask = function() {
    var input = request.readInputText();
    var responseBody = JSON.parse(input);
    var connection = datasource.getConnection();
    try {
        var sql = "UPDATE TASK SET ";
        sql += "NAME = ?";
        sql += ",";
        sql += "DESCRIPTION = ?";
        sql += ",";
        sql += "ASSIGNEE = ?";
        sql += ",";
        sql += "PRIORITY = ?";
        sql += " WHERE ID = ?";
        var statement = connection.prepareStatement(sql);
        var i = 0;
        statement.setString(++i, responseBody.name);
        statement.setString(++i, responseBody.description);
        statement.setString(++i, responseBody.assignee);
        statement.setInt(++i, responseBody.priority);
        var id = responseBody.id;
        statement.setInt(++i, id);
        statement.executeUpdate();
		response.println(id);
    } catch(e){
        var errorCode = response.BAD_REQUEST;
        exports.printError(errorCode, errorCode, e.message, sql);
    } finally {
        connection.close();
    }
};

// delete entity
exports.deleteTask = function(id) {
    var connection = datasource.getConnection();
    try {
    	var sql = "DELETE FROM TASK WHERE " + exports.pkToSQL();
        var statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        statement.executeUpdate();
        response.println(id);
    } catch(e){
        var errorCode = response.BAD_REQUEST;
        exports.printError(errorCode, errorCode, e.message, sql);
    } finally {
        connection.close();
    }
};

exports.countTask = function() {
    var count = 0;
    var connection = datasource.getConnection();
    try {
    	var sql = 'SELECT COUNT(*) FROM TASK';
        var statement = connection.prepareStatement(sql);
        var rs = statement.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch(e){
        var errorCode = response.BAD_REQUEST;
        exports.printError(errorCode, errorCode, e.message, sql);
    } finally {
        connection.close();
    }
    response.println(count);
};

exports.metadataTask = function() {
	var entityMetadata = {
		name: 'task',
		type: 'object',
		properties: []
	};
	
	var propertyid = {
		name: 'id',
		type: 'integer',
	key: 'true',
	required: 'true'
	};
    entityMetadata.properties.push(propertyid);

	var propertyname = {
		name: 'name',
		type: 'string'
	};
    entityMetadata.properties.push(propertyname);

	var propertydescription = {
		name: 'description',
		type: 'string'
	};
    entityMetadata.properties.push(propertydescription);

	var propertyassignee = {
		name: 'assignee',
		type: 'string'
	};
    entityMetadata.properties.push(propertyassignee);

	var propertypriority = {
		name: 'priority',
		type: 'integer'
	};
    entityMetadata.properties.push(propertypriority);


	response.println(JSON.stringify(entityMetadata));
};

exports.getPrimaryKeys = function() {
    var result = [];
    var i = 0;
    result[i++] = 'ID';
    if (result === 0) {
        throw new Error("There is no primary key");
    } else if(result.length > 1) {
        throw new Error("More than one Primary Key is not supported.");
    }
    return result;
};

exports.getPrimaryKey = function() {
	return exports.getPrimaryKeys()[0].toLowerCase();
};

exports.pkToSQL = function() {
    var pks = exports.getPrimaryKeys();
    return pks[0] + " = ?";
};

exports.hasConflictingParameters = function(id, count, metadata) {
    if(id !== null && count !== null){
    	exports.printError(response.EXPECTATION_FAILED, 1, "Expectation failed: conflicting parameters - id, count");
        return true;
    }
    if(id !== null && metadata !== null){
    	exports.printError(response.EXPECTATION_FAILED, 2, "Expectation failed: conflicting parameters - id, metadata");
        return true;
    }
    return false;
};

// check whether the parameter exists 
exports.isInputParameterValid = function(paramName) {
	var param = xss.escapeSql(request.getAttribute("path"));
	if (!param) {
		param = xss.escapeSql(request.getParameter(paramName));
	}
    if(param === null || param === undefined){
    	exports.printError(response.PRECONDITION_FAILED, 3, "Expected parameter is missing: " + paramName);
        return false;
    }
    return true;
};

// print error
exports.printError = function(httpCode, errCode, errMessage, errContext) {
    var body = {'err': {'code': errCode, 'message': errMessage}};
    response.setStatus(httpCode);
    response.setHeader("Content-Type", "application/json");
    response.print(JSON.stringify(body));
    console.error(JSON.stringify(body));
    if (errContext !== null) {
    	console.error(JSON.stringify(errContext));
    }
};
