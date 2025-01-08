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

    return <>
        <h1>Добро пожаловать в комнату</h1>
        <p>Код приглашения: <div className={"accent-green-700"}>{room?.joinCode}</div></p>
        <p>Список текущих игроков:
            {room?.characters?.map(character => <p>{character.user?.username}</p>)}
        </p>
        <p>
            Создайте персонажа:
        </p>
        <SelectCharacteristics/>

        <button onClick={startGameButtonHandler} type={"button"}>НАЧАТЬ ИГРУ Я УСТАЛ ЖДАТЬ</button>

    </>
}