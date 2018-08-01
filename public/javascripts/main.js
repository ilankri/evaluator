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


function sendTaskRequest(method, id, onSuccess) {
    sendRequest(method, "/tasks/" + id + "/users", onSuccess);
}

function removeElementById(id) {
    const elem = document.getElementById(id);

    elem.parentNode.removeChild(elem);
}

function registerTaskRequest(id) {
    sendTaskRequest("POST", id, function() {
        removeElementById("button" + id);
    });
}

function unregisterTaskRequest(id) {
    sendTaskRequest("DELETE", id, function() {
        removeElementById("task" + id);
    });
}
