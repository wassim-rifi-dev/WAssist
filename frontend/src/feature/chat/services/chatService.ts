import { api } from "../../../shared/services/api";

export const ask = async (prompt: string) => {
    try {
        const response = await api.post("/chat/ask", { prompt });
        return response.data;
    } catch (error) {
        console.error("Error sending prompt:", error);

        throw error;
    }
};