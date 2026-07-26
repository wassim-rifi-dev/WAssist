import PromptInput from "./feature/chat/components/PromptInput";
import useSendPrompt from "./feature/chat/hooks/useSendPrompt";

export default function App() {
  const { handleSubmit , handleChange , prompt } = useSendPrompt();
  return (
    <div className="flex items-center justify-center w-full h-screen bg-[#10131A]">
      <PromptInput handleSubmit={handleSubmit} handleChange={handleChange} prompt={prompt} />
    </div>
  )
}