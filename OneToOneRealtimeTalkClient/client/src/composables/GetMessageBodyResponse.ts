import axios from 'axios';
export function useGetMessageBodyResponse() {
    async function getMessageBodyResponse(roomId: string) {
        try {
            const messageBodyResponse = await axios.get(`http://localhost:8080/api/${roomId}`);
            const messageBodyJsonObj = messageBodyResponse.data;
            console.log("messageBodyJsonObj:", messageBodyJsonObj);
            return messageBodyJsonObj;
        } catch (error) {
            console.error("Error getting message body:", error);
            return null;
        }
    }

    return {
        getMessageBodyResponse
    };
}