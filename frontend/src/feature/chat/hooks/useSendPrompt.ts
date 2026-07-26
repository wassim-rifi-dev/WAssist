import React, { useState, type ChangeEvent } from "react";
import { ask } from "../services/chatService";

export default function useSendPrompt() {
    const [prompt , setPrompt] = useState<string>("");

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        setPrompt(e.target.value);
    };

    const sendPrompt = async (message: string) => {
        try {
            const res = await ask(message);
            return res.data;
        } catch (error) {
            console.error(error);

            throw error;
        }
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if(prompt.trim() === "") return;

        try {
            sendPrompt(prompt);
        } catch (error) {
            console.error(error);
        }
    };

    return {
        prompt,
        handleChange,
        handleSubmit
    }
}