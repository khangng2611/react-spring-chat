const FromMessage = ({ content }) => {
    return (
        <div className="col-start-6 col-end-13 p-3 rounded-lg">
            <div className="flex items-center justify-start flex-row-reverse">
                <div className="flex items-center justify-center h-10 w-10 rounded-full text-red-50 bg-red-500 flex-shrink-0" >
                    Me
                </div>
                <div className="relative mr-3 text-sm bg-red-100 py-2 px-4 shadow rounded-xl" >
                    <div>{content}</div>
                </div>
            </div>
        </div>
    )
}

export default FromMessage;