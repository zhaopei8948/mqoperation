var DEBUG = false;

function openWebSocket(onMessage, qid, qmid) {
    var wsServer = 'ws://' + location.host + "/maintain/monitors/queuews";
    if (DEBUG) {
        wsServer = 'ws://127.0.0.1:8082/maintain/monitors/queuews';
    }
    if (undefined != qid) {
        wsServer += "?qid=" + qid;
    } else if(undefined != qmid) {
        wsServer += "?qmid=" + qmid;
    }
    var websocket = new WebSocket(wsServer);

    websocket.onopen = function() {
        console.info("websocket connect open")
    };

    websocket.onmessage = function (ev) {
        console.info("recv msg = " + ev.data);
        onMessage(ev.data);
    };

    websocket.onclose = function (ev) {
        console.info("websocket close")
    };
}