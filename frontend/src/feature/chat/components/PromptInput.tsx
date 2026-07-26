import type { ChangeEvent } from "react";
import Input from "./Prompt_Input/Input";
import SubmitButton from "./Prompt_Input/SubmitButton";

type Props = {
    handleSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
    handleChange: (e: ChangeEvent<HTMLInputElement>) => void;
    prompt: string;
};

export default function PromptInput({ handleSubmit , handleChange , prompt }: Props) {
    return (
        <form
            className="flex gap-10 bg-[#1D2027] p-5 rounded-2xl"
            onSubmit={ handleSubmit }
        >
            <Input handleChange={handleChange} prompt={prompt} />
            <SubmitButton />
        </form>
    )
}