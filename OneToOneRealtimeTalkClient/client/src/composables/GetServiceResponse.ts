import axios from 'axios';
export function useGetServiceResponse() {
    // get service response if status is not success return null else return serviceResponse object
    async function getServiceResponse() {
        try {
            const serviceResponse = await axios.get('http://localhost:8080/api/createChartService');
            const serviceStatusObj = serviceResponse.data;
            return serviceStatusObj;
        } catch (error) {
            console.error("Error creating chat service:", error);
            return null;
        }
    }

    return {
        getServiceResponse
    };
}