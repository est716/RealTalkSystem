import { Ref } from "vue";

export function useGetMessageList() {
    function addMessageList(messageList: Ref<any[], any[]>, messageBody: any, userId: string) {
        const message = messageBody.record.chartRecord;
        for (let i = 0; i < message.length; i++) {
            const isMe = message[i].userId === userId;
            messageList.value.push({ id: crypto.randomUUID(), text: message[i].context, isMe: isMe });
        }
    }
    return {
        addMessageList
    };
}
