import PromptInput from "../components/PromptInput";
import ResponseShow from "../components/ResponseShow";
import useSendPrompt from "../hooks/useSendPrompt";


export default function Chat() {
    const { handleSubmit , handleChange , prompt , thinking , response } = useSendPrompt();
    return (
        <div className="flex flex-col gap-20 items-center justify-center w-full h-screen bg-[#10131A]">
            <ResponseShow response={response} />
            <PromptInput handleSubmit={handleSubmit} handleChange={handleChange} prompt={prompt} thinking={thinking} />
        </div>
    )
}