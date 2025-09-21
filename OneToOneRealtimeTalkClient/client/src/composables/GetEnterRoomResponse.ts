import { useRouter } from 'vue-router';
import axios from 'axios';
export function useGetEnterRoomResponse() {
    const router = useRouter();
    // get enter room response if status is not success return null else return enterRoomResponse object
    async function getEnterRoomResponse(userId: string) {
        try {
            const enterRoomResponse = await axios.post('http://localhost:8080/api/EnterRoom', { userId: userId });
            const enterRoomStatusObj = enterRoomResponse.data;
            switch (enterRoomStatusObj.status) {
                case "Room error":
                case "User error":
                    console.error("Failed to enter chat room, ", enterRoomStatusObj.message);
                    return null;
                case "success":
                    router.replace({ name: 'ChatRoom', params: { roomId: enterRoomStatusObj.roomId } });
                    break;
            }
            return enterRoomStatusObj;
        } catch (error) {
            console.error("Error entering room:", error);
            return null;
        }
        
    }
    return {
        getEnterRoomResponse
    }

}