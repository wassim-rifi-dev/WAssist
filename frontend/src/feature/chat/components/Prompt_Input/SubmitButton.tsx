import { SendHorizontal } from "lucide-react";

type Props = {
    thinking: boolean;
    prompt: string
}

export default function SubmitButton({ thinking , prompt }: Props) {
    return (
        <button 
            type="submit" 
            disabled={thinking || prompt.length === 0}
            className="flex items-center justify-center bg-[#ADC6FF] text-[#18437E] gap-1.5 p-1.5 rounded-lg cursor-pointer disabled:bg-[#6F7FA3]"
        >
            <span>
                {
                    thinking ? "Thinking..." : "Send"
                }
            </span>
            {
                thinking ? "" : <SendHorizontal size={15} />
            }
        </button>
    );
}