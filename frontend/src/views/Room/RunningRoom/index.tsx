import {useGetRoomStateQuery} from "../../../store/types.generated";
import {useEffect} from "react";
import {BunkerInformation} from "../../../components/BunkerInformation";
import {CharactersTable} from "../../../components/CharactersTable";
import {useParams} from "react-router-dom";

export function RunningRoom(){
    const { roomId: roomIdStr } = useParams<{ roomId: string }>()
    const roomId = Number(roomIdStr)
    const {data: roomData, refetch: refetchRoomData } = useGetRoomStateQuery({roomId: roomId})

    useEffect(() => {
        const intervalId = setInterval(refetchRoomData, 200)
        return () => clearInterval(intervalId)
    }, [refetchRoomData])

    return <div>
        <p>ДОБРО ПОЖАЛОВАТЬ В БУНКЕР</p>
        <BunkerInformation roomData={roomData}/>
        <CharactersTable roomData={roomData} canOpen={true}/>
    </div>
}