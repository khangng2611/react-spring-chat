import SockJS from 'sockjs-client';
import Stomp, { Frame, Client } from 'stompjs';
import { UserDetailsSchema, OnlineUserSchema, MessageSchema } from '../context/SessionContext';

class WebSocketsClient {
    private client: Client | null;
    private id: number | null;
    private username: string | null;
    private fullName: string | null;
    private newOnlineReceived: Array<OnlineUserSchema> | null = null;
    private setNewOnlineUser: (userList: Array<OnlineUserSchema>) => void;
    private newMessageReceived: Array<MessageSchema> | null = null;
    private setNewMessage: (message: Array<MessageSchema>) => void;

    constructor(
        userInfo: UserDetailsSchema, 
        setNewOnlineUser: (userList: Array<OnlineUserSchema>) => void,
        setNewMessage: (message: Array<MessageSchema>) => void
    ) {
        this.id = userInfo.id;
        this.username = userInfo.username;
        this.fullName = userInfo.fullName;
        this.newOnlineReceived = [];
        this.setNewOnlineUser = setNewOnlineUser;
        this.newMessageReceived = [];
        this.setNewMessage  = setNewMessage;
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
        const content: OnlineUserSchema = JSON.parse(message.body);
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
        const newMessage: MessageSchema = JSON.parse(message.body);
        const checkIfExisted = this.newMessageReceived!.some(message => message.id === newMessage.id);
        if (!checkIfExisted) {
            this.newMessageReceived!.push(newMessage);
        }
        this.setNewMessage(this.newMessageReceived!);
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

    public sendMessage = (receiverId: number, message: string) => {
        if (!receiverId) return;
        this.client?.send(
            '/app/chat',
            {},
            JSON.stringify({
                senderId: this.id,
                receiverId: receiverId,
                content: message,
            })
        );
    }
}

export default WebSocketsClient;
