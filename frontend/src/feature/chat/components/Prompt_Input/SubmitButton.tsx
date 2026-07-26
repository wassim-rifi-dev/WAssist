import { SendHorizontal } from "lucide-react";

export default function SubmitButton() {
    return (
        <button 
            type="submit" 
            className="flex items-center justify-center bg-[#ADC6FF] text-[#18437E] gap-1.5 p-1.5 rounded-lg cursor-pointer"
        >
            <span>Send</span>
            <SendHorizontal size={15} />
        </button>
    );
}