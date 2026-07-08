import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let client = null;
let subscriptions = {};
let listeners = {};

export const connectSocket = (
    deviceId,
    callback
) => {

    if(client){

    if(client.connected){
        subscribe(
            deviceId,
            callback
        );
    } else {

        setTimeout(() => {
            subscribe(
                deviceId,
                callback
            );
        },1000);
    }

    return;
}

    const socket =
        new SockJS(
            "http://localhost:8080/ws"
        );

    client = new Client({

        webSocketFactory:
            () => socket,

        reconnectDelay: 5000,

        onConnect: () => {

            console.log(
                "CONNECTED"
            );

            subscribe(
                deviceId,
                callback
            );
        },

        onStompError: frame =>
            console.log(frame),

        onWebSocketError: err =>
            console.log(err)
    });

    client.activate();
};

function subscribe(
    deviceId,
    callback
){
    if (!listeners[deviceId]) {
    listeners[deviceId] = [];
}

if (!listeners[deviceId].includes(callback)) {
    listeners[deviceId].push(callback);
}

    if(
        subscriptions[deviceId]
    ){
        return;
    }

    console.log(
        "SUBSCRIBING:",
        deviceId
    );

    subscriptions[deviceId] =
        client.subscribe(

            `/topic/device/${deviceId}`,

            message => {

                console.log(
                    "MESSAGE RECEIVED",
                    message.body
                );

                // callback(
                //     JSON.parse(
                //         message.body
                //     )
                // );
                const data = JSON.parse(message.body);

            listeners[deviceId].forEach(cb =>
                cb(data)
            );
            }
        );
}

export const disconnectSocket =
() => {

    Object.values(subscriptions)
      .forEach(s => s.unsubscribe() );

    subscriptions = {};

    if(client){
        client.deactivate();
        client = null;
    }
};