import SockJS from 'sockjs-client';
import Stomp, { Frame, Client } from 'stompjs';
import { UserDetailsSchema, OnlineListSchema } from '../context/SessionContext';

class WebSocketsClient {
    private client: Client | null;
    private id: number | null;
    private username: string | null;
    private fullName: string | null;
    private newOnlineReceived: Array<OnlineListSchema> | null = null;
    private setNewOnlineUser: (userList: Array<OnlineListSchema>) => void;

    constructor(userInfo: UserDetailsSchema, setNewOnlineUser: (userList: Array<OnlineListSchema>) => void) {
        this.id = userInfo.id;
        this.username = userInfo.username;
        this.fullName = userInfo.fullName;
        this.newOnlineReceived = [];
        this.setNewOnlineUser = setNewOnlineUser;
        const socket = new SockJS(`${process.env.REACT_APP_BE_URL}/ws` || '');
        this.client = Stomp.over(socket);
        this.client.connect({}, this.onConnected, this.onError);
    }

    private onConnected = () => {
        if (!this.client) return;
        while (!this.client.connected) { }
        this.client.subscribe('/online', this.onNewOnlineReceived);
        this.client.subscribe(
            `/user/${this.id}/queue/messages`,
            this.onMessageReceived
        );
        this.client.send(
            '/app/user.addUser',
            {},
            JSON.stringify({
                id: this.id,
                username: this.username,
                fullName: this.fullName,
            })
        );
    };

    private onError = (error: string | Frame) => {
        console.log('Error: ', error);
    };

    private onNewOnlineReceived = (message: Stomp.Message) => {
        const content: OnlineListSchema = JSON.parse(message.body);
        const existingUser = this.newOnlineReceived!.find(user => user.id === content.id);
        if (existingUser) {
            // User already exists, update their information
            existingUser.username = content.username;
            existingUser.fullName = content.fullName;
            existingUser.status = content.status;
        } else {
            this.newOnlineReceived!.push(content);
        }
        this.newOnlineReceived = this.newOnlineReceived?.length ? this.newOnlineReceived?.filter((user) => user.id !== this.id) : null;
        this.setNewOnlineUser(this.newOnlineReceived!);
    };

    public onMessageReceived = (message: Stomp.Message) => {
        console.log(`onMessageReceived: ${message.body}`);
    };

    public onDisconnected = () => {
        this.client?.send(
            '/app/user.disconnectUser',
            {},
            JSON.stringify({
                id: this.id,
                username: this.username,
                fullName: this.fullName,
            })
        );
    };

    public sendMessage = (message: string) => {
        this.client?.send(
            '/app/chat',
            {},
            JSON.stringify({
                sender: this.id,
                content: message,
            })
        );
    }

    // public getNewOnlineUser = () => {
    //     return this.newOnlineReceived;
    // }
}

export default WebSocketsClient;
