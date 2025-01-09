import {useGetApiMeQuery, useGetRoomStateQuery, useStartGameMutation} from "../../../store/types.generated";
import {useEffect} from "react";
import {SelectCharacteristics} from "../../../components/SelectCharacteristics";
import toast from "react-hot-toast";
import {useParams} from "react-router-dom";

export function NewRoom() {
    const { roomId: roomIdStr } = useParams<{ roomId: string }>()
    const roomId = Number(roomIdStr)
    const {data: room, refetch: refetchRoom} = useGetRoomStateQuery({roomId: Number(roomId)})
    const [startGame,] = useStartGameMutation()

    useEffect(() => {
        const intervalId = setInterval(refetchRoom, 1000)
        return () => clearInterval(intervalId)
    }, [refetchRoom])

    async function startGameButtonHandler() {
        await startGame({roomId: roomId}).unwrap()
            .then(() => toast.success("Игра начата!"))
            .catch(() => toast.error("Игру начать не удалось! Слишком мало участников!"))
    }

    return (
        <div className="min-h-screen bg-burgundy-900 text-burgundy-200 flex flex-col items-center justify-center p-8">
            <header className="w-full text-center mb-6">
                <h1 className="text-4xl font-bold text-burgundy-200">Добро пожаловать в комнату</h1>
            </header>

            <div className="bg-burgundy-950 p-6 rounded-lg shadow-[0_10px_10px_rgba(0,0,0,0.5),_0_-3px_10px_rgba(0,0,0,0.5)] shadow-burgundy-500/30 w-full max-w-3xl mb-8">
                <p className="text-xl text-gray-300 mb-2">
                    Код приглашения:
                    <span className="text-green-600 font-semibold">{room?.joinCode}</span>
                </p>

                <div className="mb-4">
                    <p className="text-lg text-gray-300">Список текущих игроков:</p>
                    <ul className="space-y-2">
                        {room?.characters?.map((character, index) => (
                            <li key={index} className="text-gray-200">{character.user?.username}</li>
                        ))}
                    </ul>
                </div>
            </div>

            <div className="bg-burgundy-950 p-6 rounded-lg shadow-[0_10px_10px_rgba(0,0,0,0.5),_0_-3px_10px_rgba(0,0,0,0.5)] shadow-burgundy-500/30 w-full max-w-7xl mb-8">
                <p className="text-lg text-gray-300 mb-4">Создайте персонажа:</p>
                <SelectCharacteristics />
            </div>

            <button
                onClick={startGameButtonHandler}
                type="button"
                className="w-full py-3 bg-blue-600 hover:bg-blue-700 text-white font-semibold text-xl rounded-lg transition duration-300 transform hover:scale-105 mt-6"
            >
                НАЧАТЬ ИГРУ Я УСТАЛ ЖДАТЬ
            </button>
        </div>
    );
}