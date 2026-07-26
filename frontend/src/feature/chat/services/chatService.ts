import { api } from "../../../shared/services/api";

export const ask = async (message: string) => {
    try {
        const response = await api.post("/chat/ask", { message });
        return response.data;
    } catch (error) {
        console.error("Error sending prompt:", error);

        throw error;
    }
};