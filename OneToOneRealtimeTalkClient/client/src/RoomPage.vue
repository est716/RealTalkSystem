<template>
  <div class="chat-container">
    <!-- Room Title -->
    <div class="chat-header">
      <h2>Room Name</h2>
    </div>

    <!-- Chat Messages -->
    <div class="chat-messages">
      <div v-for="msg in messageList" :key="msg.id" :class="['chat-message', msg.isMe ? 'me' : 'other']">
        {{ msg.text }}
      </div>
    </div>

    <!-- Input Area -->
    <div class="chat-input">
      <input v-model="messageModel" type="text" placeholder="輸入訊息..." @keyup.enter="sendMessage(roomId, userId)"/>
      <button @click="sendMessage(roomId, userId)">送出</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue';
import { useSendMessage } from '@/composables/SendMessage';
import { useGetMessageList } from '@/composables/GetMessageList';
import { useGetServiceResponse } from '@/composables/GetServiceResponse';
import { useGetEnterRoomResponse } from '@/composables/GetEnterRoomResponse';
import { useGetMessageBodyResponse } from '@/composables/GetMessageBodyResponse';
const userId = crypto.randomUUID();
const roomId = ref('');
const messageList = ref([]);
const socket = ref(null);
const { messageModel, sendMessage } = useSendMessage(socket, messageList);
const { addMessageList } = useGetMessageList();
const { getServiceResponse } = useGetServiceResponse();
const { getEnterRoomResponse } = useGetEnterRoomResponse();
const { getMessageBodyResponse } = useGetMessageBodyResponse();
onMounted(async () => {
  // Get server response
  const serviceResponse = await getServiceResponse();
  if (serviceResponse === null) {
    alert("伺服器異常，請稍後再試");
    return;
  }
  // Get enter room response
  const enterRoomStatusObj = await getEnterRoomResponse(userId);
  if (enterRoomStatusObj === null) {
    alert("進入房間失敗，請稍後再試");
    return;
  }

  // Get message body response
  roomId.value = enterRoomStatusObj.roomId;
  const messageBodyJsonObj = await getMessageBodyResponse(roomId.value);
  addMessageList(messageList, messageBodyJsonObj, userId);

  socket.value = new WebSocket('ws://localhost:8080/socket');
  socket.value.onopen = () => {
    console.log('WebSocket 已連線');
  };

  socket.value.onerror = (err) => {
    console.error('WebSocket 連線失敗', err);
  };

  socket.value.onmessage = (event) => {
    const receivedData = event.data;
    const parsedData = JSON.parse(receivedData);
    if (parsedData.userId !== userId) {
      messageList.value.push({ id: crypto.randomUUID(), text: parsedData.context, isMe: false });
    }
  };

})

onBeforeUnmount(() => {
  if (socket.value && socket.value.readyState === WebSocket.OPEN) {
    socket.value.close(1000, 'Client closed the connection');
  }
});

window.addEventListener('beforeunload', () => {
  if (socket.value && socket.value.readyState === WebSocket.OPEN) {
    socket.value.close(1000, 'Client closed the connection');
  }
});
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f4f6f8;
  font-family: Arial, sans-serif;
}

/* Header 標題區 */
.chat-header {
  background-color: #a7b4fd;
  color: white;
  padding: 1rem;
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
}

/* 訊息區塊 */
.chat-messages {
  flex: 1;
  padding: 1rem;
  overflow-y: auto;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

/* 共通訊息樣式 */
.chat-message {
  max-width: 70%;
  padding: 0.5rem 1rem;
  border-radius: 16px;
  word-break: break-word;
}

/* 我的訊息：靠右 + 藍色 */
.chat-message.me {
  align-self: flex-end;
  background-color: #d2e3fc;
  color: #0b3d91;
  border-top-right-radius: 0;
}

/* 別人的訊息：靠左 + 灰色 */
.chat-message.other {
  align-self: flex-start;
  background-color: #e0e0e0;
  color: #333;
  border-top-left-radius: 0;
}

/* 輸入區塊 */
.chat-input {
  display: flex;
  padding: 1rem;
  background-color: #fafafa;
  gap: 0.5rem;
  border-top: 1px solid #ccc;
}

.chat-input input[type="text"] {
  flex: 1;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  border: 1px solid #ccc;
  font-size: 1rem;
}

.chat-input button {
  padding: 0.5rem 1rem;
  background-color: #3f51b5;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.chat-input button:hover {
  background-color: #303f9f;
}
</style>