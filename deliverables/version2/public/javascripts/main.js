function sendRequest(method, url) {
    const request = new XMLHttpRequest();

    request.open(method, url, true);
    request.send(null);
}


function sendTaskRequest(method, taskId, userId) {
    sendRequest(method, "/tasks/" + taskId + "/users/" + userId);
}

function registerTaskRequest(taskId, userId) {
    sendTaskRequest("POST", taskId, userId);
}

function unregisterTaskRequest(taskId, userId) {
    sendTaskRequest("DELETE", taskId, userId);
}
