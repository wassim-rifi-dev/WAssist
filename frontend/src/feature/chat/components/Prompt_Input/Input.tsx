import type { ChangeEvent } from "react";

type Props = {
    handleChange: (e: ChangeEvent<HTMLInputElement>) => void;
    prompt: string;
};

export default function Input({handleChange , prompt}: Props) {
    return (
        <input 
            type="text"
            placeholder="Chat with WAssist..."
            className="placeholder:text-[#41434A] outline-0 text-white"
            value={prompt}
            onChange={handleChange}
        />
    )
}