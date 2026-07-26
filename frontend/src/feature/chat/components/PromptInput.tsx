import Input from "./Prompt_Input/Input";
import SubmitButton from "./Prompt_Input/SubmitButton";

export default function PromptInput() {
    return (
        <div className="flex gap-10 bg-[#1D2027] p-5 rounded-2xl">
            <Input />
            <SubmitButton />
        </div>
    )
}