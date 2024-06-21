import SockJS from 'sockjs-client';
import Stomp, { Frame, Client } from 'stompjs';

class WebSocketsClient {
    private client: Client | null;
    private username: string | null
    private fullName: string | null

    constructor(userInfo : { username: string, fullName: string}) {
        this.username = userInfo.username
        this.fullName = userInfo.fullName
        const socket = new SockJS(process.env.REACT_APP_BROKER_URL || '');
        this.client = Stomp.over(socket);
        this.client.connect({}, this.onConnected, this.onError);
    }

    private onConnected = () => {
        if (!this.client) return;
        while (!this.client.connected) {}
        this.client.subscribe(`/online`, this.onMessageReceived);
        this.client.subscribe(
            `/user/${this.username}/queue/messages`,
            this.onMessageReceived
        );
        this.client.send(
            '/app/user.addUser',
            {},
            JSON.stringify({
                username: this.username,
                fullName: this.fullName,
                status: 'ONLINE',
            })
        );
    };

    private onError = (error: string | Frame) => {
        console.log('Error: ', error);
    };

    private onMessageReceived = (message: Stomp.Message) => {
        console.log(`Received: ${message.body}`);
    };

    public onDisconnected = () => {
        if (!this.client) return ;
        console.log(`Disconnected: ${this.client}`);
        this.client.send(
            '/app/user.disconnectUser',
            {},
            JSON.stringify({
                username: this.username,
                fullName: this.fullName,
                status: 'OFFLINE',
            })
        );
    };
}

export default WebSocketsClient;
