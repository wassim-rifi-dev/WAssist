import React, { useState, type ChangeEvent } from "react";
import { ask } from "../services/chatService";

export default function useSendPrompt() {
    const [prompt , setPrompt] = useState<string>("");
    const [thinking , setThinking] = useState<boolean>(false);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        setPrompt(e.target.value);
    };

    const sendPrompt = async (message: string) => {
        try {
            setThinking(true);

            const res = await ask(message);
            setPrompt("");
            return res.data;
        } catch (error) {
            setThinking(false);
            console.error(error);
            throw error;
        } finally {
            setThinking(false);
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
        thinking,
        handleChange,
        handleSubmit
    }
}