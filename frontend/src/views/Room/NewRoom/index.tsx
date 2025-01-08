import {useGetRoomStateQuery} from "../../../store/types.generated";
import {useEffect} from "react";

export function NewRoom() {
    //todo roomId
    const {data: room, refetch: refetchRoom} = useGetRoomStateQuery({roomId: 0})

    useEffect(() => {
        const intervalId = setInterval(refetchRoom, 1000)
        return () => clearInterval(intervalId)
    }, [refetchRoom])


    return <>
        <h1>Добро пожаловать в комнату</h1>
        <p>Код приглашения: <div className={"accent-green-700"}>{room?.joinCode}</div></p>
        <p>Список текущих игроков:
            {room?.characters?.map(character => <p>{character.user?.login}</p>)}
        </p>

    </>
}