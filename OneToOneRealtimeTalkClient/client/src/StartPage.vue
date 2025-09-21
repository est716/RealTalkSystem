<template>
  <div class="container">
    <div class="card">
      <h1>匿名加入聊天室</h1>
      <input type="button" value="加入聊天室" class="join-button" @click="joinChatRoom"/>
    </div>
  </div>
</template>
<script setup>
    import { useRouter } from 'vue-router'

    const router = useRouter()

    function joinChatRoom() {
        router.push({ name: 'ChatRoom' })
    }
</script>
<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #f0f4f8, #d9e2ec);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.card {
  background-color: white;
  padding: 2rem 3rem;
  border-radius: 16px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  margin-bottom: 1.5rem;
  color: #334e68;
  font-size: 1.8rem;
}

.join-button {
  padding: 0.75rem 2rem;
  font-size: 1rem;
  border: none;
  border-radius: 12px;
  background-color: #486581;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.join-button:hover {
  background-color: #243b53;
}
</style>
