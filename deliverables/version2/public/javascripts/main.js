function sendRequest(method, url, onSuccess) {
    const request = new XMLHttpRequest();

    request.open(method, url, true);
    request.send(null);

    /* On success, update the view accordingly.  */
    request.onreadystatechange = function() {
        if(request.readyState == XMLHttpRequest.DONE && request.status == 200) {
            onSuccess();
        }
    };
}


function sendTaskRequest(method, taskId, userId, onSuccess) {
    sendRequest(method, "/tasks/" + taskId + "/users/" + userId, onSuccess);
}

function removeElementById(id) {
    const elem = document.getElementById(id);

    elem.parentNode.removeChild(elem);
}

function registerTaskRequest(taskId, userId) {
    sendTaskRequest("POST", taskId, userId, function() {
        removeElementById("button" + taskId);
    });
}

function unregisterTaskRequest(taskId, userId) {
    sendTaskRequest("DELETE", taskId, userId, function() {
        removeElementById("task" + taskId);
    });
}
