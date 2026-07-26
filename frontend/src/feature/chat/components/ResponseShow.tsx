type Props = {
    response: string
}

export default function ResponseShow({ response }: Props) {
    return (
        <div>
            <p className="text-white">
                {
                    response
                }
            </p>
        </div>
    )
}