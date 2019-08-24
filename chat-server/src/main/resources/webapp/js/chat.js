function redirect(url, params) {
    window.location.replace(paramsToQuery(url, params));
}

function paramsToQuery(url, parmas) {
    var urlWithQuery = url;
    var index = 0;
    var count = Object.keys(parmas).length;
    for (var key in parmas) {
        if (parmas.hasOwnProperty(key)) {
            console.log(key, parmas[key]);
            if (index === 0) {
                urlWithQuery = urlWithQuery + "?";
            }
            urlWithQuery = urlWithQuery + key + "=" + parmas[key];

            if (index !== count - 1) {
                urlWithQuery = urlWithQuery + "&";
            }
            index = index + 1;
        }
    }
    console.debug("url with query: " + urlWithQuery);
    return urlWithQuery;
}


function getLocation(href) {
    var l = document.createElement("a");
    l.href = href;
    return l;
}

function connectToWS(endpoint, onMsg, onOpen, onClose, onErr) {
    var ws = new WebSocket(endpoint);
    ws.onmessage = onMsg;
    ws.onopen = onOpen;
    ws.onclose = onClose;
    ws.onerror = onErr;
    return ws;
}