import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

let client : Stomp.Client;

const initWebSockets = () => {
    const socket = new SockJS(process.env.REACT_APP_BROKER_URL || '');
    client = Stomp.over(socket);
    client.connect(
        {
            login : "",
            passcode: ""
        }, 
        onConnected, 
    );
}

const onConnected = () => {
    while (!client.connected) {}
    let user = {
        id: 1,
        username: 'stompjs',
        fullName: 'stompjs'
    }
    // subcribe to get messages sent to this user
    client.subscribe(`/user/${user.id}/queue/messages`, onMessageReceived);
    // subcribe to get list of online
    client.subscribe(`/user/topic`, onMessageReceived);
    // publish to notify this user is online
    client.send(
        '/app/user.addUser',
        {},
        JSON.stringify({
            id: user.id,
            username: user.username,
            fullName: user.fullName
        })
    );
}

const onError = (error: string) => {
    console.log('Error: ', error);
}

const onMessageReceived = (message: Stomp.Message) => {
    console.log(`Received: ${message.body}`);
}

export { initWebSockets }
