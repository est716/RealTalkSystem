import { ref, computed, Ref } from "vue";

export function useSendMessage(socket: Ref<any, any>, messageList: Ref<any[], any[]>) {
    const myMessage = ref('');

    const messageModel = computed({
        get: () => myMessage.value,
        set: (value) => myMessage.value = value
    });

    function sendMessage(roomId: any, userId: any) {
        if (myMessage.value.trim() === '') {
            return;
        }

        if (socket.value !== null && socket.value.readyState === WebSocket.OPEN) {
            const jsonString = JSON.stringify({
                roomId: roomId,
                userId: userId,
                context: myMessage.value
            });
            messageList.value.push({ id: crypto.randomUUID(), text: myMessage.value, isMe: true });
            try {
                socket.value.send(jsonString);
            } catch (err) {
                console.error('WebSocket 發送失敗', err);
            }
        }
        myMessage.value = '';
    }
    return {
        messageModel,
        sendMessage
    };
}